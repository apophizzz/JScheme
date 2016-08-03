package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.builtin.syntax.SchemeBuiltinSyntax;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunction;
import hdm.pk070.jscheme.stack.SchemeCallStack;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.LocalEnvironment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import hdm.pk070.jscheme.util.ReflectionCallArg;
import hdm.pk070.jscheme.util.ReflectionUtils;
import hdm.pk070.jscheme.util.exception.ReflectionMethodCallException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 * A test class for {@link ListEvaluator}
 *
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest({SchemeCallStack.class, SymbolEvaluator.class, LocalEnvironment.class, SchemeEval.class})
public class ListEvaluatorTest {

    private static final String METHOD_EVAL_BUILTIN_FUNC = "evaluateBuiltinFunction";
    private static final String METHOD_EVAL_CUST_USER_FUNC = "evaluateCustomUserFunction";

    private ListEvaluator listEvaluator;

    private SchemeCons argumentListWithTwoArgs;
    private SchemeCons argumentListWithSingleArg;
    private SchemeCons parameterListWithSingleParam;
    private SchemeCons parameterListWithTwoParams;


    @Before
    public void setUp() {
        this.listEvaluator = ListEvaluator.getInstance();

        this.argumentListWithTwoArgs = new SchemeCons(new SchemeInteger(2), new SchemeCons(new SchemeInteger(3), new
                SchemeNil()));
        this.argumentListWithSingleArg = new SchemeCons(new SchemeInteger(42), new SchemeNil());
        this.parameterListWithSingleParam = new SchemeCons(new SchemeSymbol("x"), new SchemeNil());
        this.parameterListWithTwoParams = new SchemeCons(new SchemeSymbol("x"), new SchemeCons(new SchemeSymbol("y"),
                new SchemeNil()));
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

        // Invoke target method via reflection
        ReflectionUtils.invokeMethod(listEvaluator, METHOD_EVAL_BUILTIN_FUNC,
                new ReflectionCallArg(SchemeBuiltinFunction.class, schemeBuiltinFunction),
                new ReflectionCallArg(SchemeObject.class, argumentListWithTwoArgs),
                new ReflectionCallArg(Environment.class, LocalEnvironment.withSize(42)));

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

    @Test
    public void testEvaluateCustomUserFunctionThrowsErrorOnMissingArgument() throws SchemeError {
        SchemeCustomUserFunction customFunction = mock(SchemeCustomUserFunction.class);
        prepareCustomUserFunctionMock(customFunction, null, parameterListWithTwoParams, 2);

        try {
            ReflectionUtils.invokeMethod(this.listEvaluator, METHOD_EVAL_CUST_USER_FUNC, new ReflectionCallArg
                    (SchemeCustomUserFunction.class, customFunction), new ReflectionCallArg(SchemeObject.class,
                    argumentListWithSingleArg), new ReflectionCallArg(Environment.class, null));
            fail("Expected exception has not been thrown!");
        } catch (ReflectionMethodCallException e) {
            assertException(e.getCause().getCause(), new SchemeError("(eval): arity mismatch, expected number of " +
                    "arguments does not match the given number [expected: 2, given: 1]"));
        }
    }

    @Test
    public void testEvaluateCustomUserFunctionAddsArgumentToEnvProperly() throws SchemeError {
        LocalEnvironment localEnvMock = mock(LocalEnvironment.class);

        PowerMockito.mockStatic(LocalEnvironment.class);
        PowerMockito.when(LocalEnvironment.withSizeAndParent(1, null)).thenReturn(localEnvMock);

        SchemeCons emptyBodyList = new SchemeCons(new SchemeNil(), new SchemeNil());
        SchemeCustomUserFunction customFunctionMock = mock(SchemeCustomUserFunction.class);
        prepareCustomUserFunctionMock(customFunctionMock, emptyBodyList, parameterListWithSingleParam, 1);

        ReflectionUtils.invokeMethod(this.listEvaluator, METHOD_EVAL_CUST_USER_FUNC, new ReflectionCallArg
                (SchemeCustomUserFunction.class, customFunctionMock), new ReflectionCallArg(SchemeObject.class,
                argumentListWithSingleArg), new ReflectionCallArg(Environment.class, null));

        verify(localEnvMock, times(1)).add(EnvironmentEntry.create((SchemeSymbol) parameterListWithSingleParam.getCar(),
                argumentListWithSingleArg.getCar()));
    }

    @Test
    public void testEvaluateCustomUserFunctionThrowsErrorOnToManyArguments() {
        SchemeCustomUserFunction customFunctionMock = mock(SchemeCustomUserFunction.class);
        prepareCustomUserFunctionMock(customFunctionMock, null, parameterListWithSingleParam, 1);

        try {
            ReflectionUtils.invokeMethod(this.listEvaluator, METHOD_EVAL_CUST_USER_FUNC, new ReflectionCallArg
                    (SchemeCustomUserFunction.class, customFunctionMock), new ReflectionCallArg(SchemeObject.class,
                    argumentListWithTwoArgs), new ReflectionCallArg(Environment.class, null));
            fail("Expected exception has not been thrown!");
        } catch (ReflectionMethodCallException e) {
            assertException(e.getCause().getCause(), new SchemeError("(eval): arity mismatch, expected number of " +
                    "arguments does not match the given number [expected: 1, more given!]"));
        }
    }

    @Test
    public void testEvaluateCustomUserFunctionReturnsExpectedResultOnValidInput() throws SchemeError {
        SchemeCons bodyList = new SchemeCons(new SchemeCons(new SchemeSymbol("+"), new SchemeCons(new SchemeSymbol
                ("x"), new SchemeCons(new SchemeSymbol("y"), new SchemeNil()))), new SchemeNil());

        SchemeCustomUserFunction customFunctionMock = mock(SchemeCustomUserFunction.class);
        prepareCustomUserFunctionMock(customFunctionMock, bodyList, parameterListWithTwoParams, 2);

        LocalEnvironment localEnvDummy = mock(LocalEnvironment.class);
        PowerMockito.mockStatic(LocalEnvironment.class);
        PowerMockito.when(LocalEnvironment.withSizeAndParent(2, null)).thenReturn(localEnvDummy);

        SchemeEval schemeEvalMock = mock(SchemeEval.class);
        when(schemeEvalMock.eval(new SchemeInteger(2), null)).thenReturn(new SchemeInteger(2));
        when(schemeEvalMock.eval(new SchemeInteger(3), null)).thenReturn(new SchemeInteger(3));
        when(schemeEvalMock.eval(bodyList.getCar(), localEnvDummy)).thenReturn(new SchemeInteger(5));

        PowerMockito.mockStatic(SchemeEval.class);
        PowerMockito.when(SchemeEval.getInstance()).thenReturn(schemeEvalMock);

        Object result = ReflectionUtils.invokeMethod(this.listEvaluator,
                METHOD_EVAL_CUST_USER_FUNC, new ReflectionCallArg(SchemeCustomUserFunction.class, customFunctionMock)
                , new ReflectionCallArg(SchemeObject.class, argumentListWithTwoArgs), new ReflectionCallArg
                        (Environment.class, null));

        assertThat(result, notNullValue());
        assertThat(result.getClass(), equalTo(SchemeInteger.class));
        assertThat(result, equalTo(new SchemeInteger(5)));
    }

    private void prepareCustomUserFunctionMock(SchemeCustomUserFunction customUserFunctionMock, SchemeCons bodyList,
                                               SchemeCons paramList, int paramCount) {
        when(customUserFunctionMock.getRequiredSlotsCount()).thenReturn(paramCount);
        when(customUserFunctionMock.getHomeEnvironment()).thenReturn(null);
        when(customUserFunctionMock.getParameterList()).thenReturn(paramList);
        when(customUserFunctionMock.getParamCount()).thenReturn(paramCount);
        when(customUserFunctionMock.getFunctionBodyList()).thenReturn(bodyList);
    }

    private void assertException(Throwable actual, Throwable expected) {
        assertThat(actual.getClass(), equalTo(expected.getClass()));
        assertThat(actual.getMessage(), equalTo(expected.getMessage()));
    }

}
