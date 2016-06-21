package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeCons;
import hdm.pk070.jscheme.obj.type.SchemeInteger;
import hdm.pk070.jscheme.obj.type.SchemeNil;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import hdm.pk070.jscheme.obj.type.function.builtin.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.type.function.builtin.SchemeBuiltinPlus;
import hdm.pk070.jscheme.stack.SchemeCallStack;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.LocalEnvironment;
import hdm.pk070.jscheme.util.ReflectionMethodParam;
import hdm.pk070.jscheme.util.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by patrick on 19.06.16.
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
        SchemeCallStack mockedStack = mock(SchemeCallStack.class);
        Mockito.when(mockedStack.push(Matchers.any(SchemeObject.class))).thenReturn(null);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedStack);

        int expectedArgsCount = 2;
        SchemeInteger addResult = new SchemeInteger(5);
        SchemeBuiltinFunction schemeBuiltinFunction = Mockito.mock(SchemeBuiltinPlus.class);
        Mockito.when(schemeBuiltinFunction.call(expectedArgsCount)).thenReturn(addResult);

        SchemeCons additionArgList = new SchemeCons(new SchemeInteger(2), new SchemeCons(new SchemeInteger(3), new
                SchemeNil()));


        SchemeObject functionCallResult = (SchemeObject) ReflectionUtils.invokeMethod(listEvaluator,
                METHOD_EVAL_BUILTIN_FUNC,
                new ReflectionMethodParam
                        (SchemeBuiltinFunction.class, schemeBuiltinFunction), new ReflectionMethodParam(SchemeObject
                        .class,
                        additionArgList), new ReflectionMethodParam(Environment.class, LocalEnvironment.withSize(42)));

        assertThat(functionCallResult, notNullValue());
        assertThat(functionCallResult.typeOf(SchemeInteger.class), equalTo(true));
        assertThat(new SchemeInteger(5).equals(functionCallResult), equalTo(true));
    }

    @Test
    public void testDoEvalWithBuiltinFunction() throws SchemeError {
        SchemeSymbol plusSymbol = new SchemeSymbol("+");
        LocalEnvironment dummyEnv = LocalEnvironment.withSize(42);

        SymbolEvaluator symbolEvaluator = mock(SymbolEvaluator.class);
        Mockito.when(symbolEvaluator.doEval(plusSymbol, dummyEnv)).thenReturn(SchemeBuiltinPlus.create());
        PowerMockito.mockStatic(SymbolEvaluator.class);
        PowerMockito.when(SymbolEvaluator.getInstance()).thenReturn(symbolEvaluator);


        SchemeObject result = listEvaluator.doEval(new SchemeCons(new SchemeSymbol("+"), new SchemeCons(new
                SchemeInteger(1), new
                SchemeCons(new SchemeInteger(2), new SchemeNil()))), dummyEnv);

        assertThat(result, notNullValue());
        assertThat(result.typeOf(SchemeInteger.class), equalTo(true));
        assertThat(result.equals(new SchemeInteger(3)), equalTo(true));
    }
}
