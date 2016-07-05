package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.builtin.syntax.SchemeBuiltinSyntax;
import hdm.pk070.jscheme.stack.SchemeCallStack;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.LocalEnvironment;
import hdm.pk070.jscheme.util.ReflectionCallArg;
import hdm.pk070.jscheme.util.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

/**
 * A test class for {@link ListEvaluator}
 *
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest({SchemeCallStack.class, SymbolEvaluator.class})
public class ListEvaluatorTest {

    private static final String METHOD_EVAL_BUILTIN_FUNC = "evaluateBuiltinFunction";

    private ListEvaluator listEvaluator;

    @Before
    public void setUp() {
        this.listEvaluator = ListEvaluator.getInstance();
    }

    @Test
    public void testEvaluateBuiltinFunction() throws SchemeError {
        int expectedArgCount = 2;

        // Setup SchemeCallStack mock and ensure its returned on request for an instance
        SchemeCallStack mockedStack = mock(SchemeCallStack.class);
        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedStack);

        // Setup SchemeBuiltinFunction mock
        SchemeBuiltinFunction schemeBuiltinFunction = Mockito.mock(SchemeBuiltinFunction.class);

        // Setup a dummy argument list
        SchemeCons functionCallArgList = new SchemeCons(new SchemeInteger(2), new SchemeCons(new SchemeInteger(3), new
                SchemeNil()));

        // Invoke target method via reflection
        ReflectionUtils.invokeMethod(listEvaluator, METHOD_EVAL_BUILTIN_FUNC, new ReflectionCallArg
                        (SchemeBuiltinFunction.class, schemeBuiltinFunction),
                new ReflectionCallArg(SchemeObject.class, functionCallArgList), new ReflectionCallArg(Environment.class,
                        LocalEnvironment.withSize(42)));

        // Ensure arguments have been pushed on stack
        verify(mockedStack).push(eq(new SchemeInteger(2)));
        verify(mockedStack).push(eq(new SchemeInteger(3)));

        // Ensure the built-in function mock has been called with the right number of arguments
        verify(schemeBuiltinFunction).call(expectedArgCount);
    }

    @Test
    public void testDoEvalWithBuiltinFunction() throws SchemeError {
        SchemeSymbol plusSymbol = new SchemeSymbol("+");
        LocalEnvironment dummyEnv = LocalEnvironment.withSize(42);

        // Setup a built-in function mock
        SchemeBuiltinFunction builtinFunctionMock = mock(SchemeBuiltinFunction.class);
        SymbolEvaluator symbolEvaluator = mock(SymbolEvaluator.class);
        Mockito.when(symbolEvaluator.doEval(plusSymbol, dummyEnv)).thenReturn(builtinFunctionMock);

        // Setup the SymbolEvaluator mock which returns the function mock
        PowerMockito.mockStatic(SymbolEvaluator.class);
        PowerMockito.when(SymbolEvaluator.getInstance()).thenReturn(symbolEvaluator);

        // Create a dummy expression (function call)
        SchemeCons expression = new SchemeCons(new SchemeSymbol("+"), new SchemeCons(new
                SchemeInteger(1), new SchemeCons(new SchemeInteger(2), new SchemeNil())));

        // Call ListEvaluator
        listEvaluator.doEval(expression, dummyEnv);

        // Verify that the function mock has been called as expected
        verify(builtinFunctionMock, times(1)).call(2);
    }

    @Test
    public void testBuiltinSyntaxIsCalledWithExpectedArgs() throws SchemeError {
        SchemeSymbol defineSymbol = new SchemeSymbol("define");
        LocalEnvironment dummyEnv = LocalEnvironment.withSize(42);

        // Setup SymbolEvaluator mock for returning our SchemeBuiltinSyntax mock
        SchemeBuiltinSyntax mockedBuiltinSyntax = mock(SchemeBuiltinSyntax.class);
        SymbolEvaluator mockedSymbolEvaluator = mock(SymbolEvaluator.class);
        when(mockedSymbolEvaluator.doEval(defineSymbol, dummyEnv)).thenReturn(mockedBuiltinSyntax);

        // Setup SymbolEvaluator class for returning our prepared mock
        PowerMockito.mockStatic(SymbolEvaluator.class);
        PowerMockito.when(SymbolEvaluator.getInstance()).thenReturn(mockedSymbolEvaluator);

        SchemeCons expression = new SchemeCons(new SchemeSymbol("define"), new SchemeCons(new SchemeSymbol("abc"),
                new SchemeCons(new SchemeInteger(42), new SchemeNil())));
        listEvaluator.doEval(expression, dummyEnv);

        // Verify that our SchemeBuiltinSyntax mock is called exactly once and with the expected arguments
        verify(mockedBuiltinSyntax, times(1)).apply(expression.getCdr(), dummyEnv);
    }
}
