package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.SchemeEval;
import hdm.pk070.jscheme.obj.builtin.simple.*;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.LocalEnvironment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import hdm.pk070.jscheme.util.ReflectionCallArg;
import hdm.pk070.jscheme.util.ReflectionUtils;
import hdm.pk070.jscheme.util.exception.ReflectionMethodCallException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest({SchemeEval.class})
public class SchemeBuiltinDefineTest {

    private SchemeBuiltinSyntax builtinDefine;
    private Environment dummyEnvironment;


    @Before
    public void setUp() {
        this.builtinDefine = SchemeBuiltinDefine.create();
        this.dummyEnvironment = LocalEnvironment.withSize(42);
    }

    @Test(expected = NullPointerException.class)
    public void testThrowExceptionIfArgListIsNull() throws SchemeError {
        this.builtinDefine.apply(null, dummyEnvironment);
    }

    @Test(expected = NullPointerException.class)
    public void testThrowExceptionIfEnvironmentIsNull() throws SchemeError {
        this.builtinDefine.apply(new SchemeInteger(42), null);
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnNonConsArgumentList() throws SchemeError {
        this.builtinDefine.apply(new SchemeString("Not a arg list"), dummyEnvironment);
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorIfConsArgHasNoSubsequentCons() throws SchemeError {
        this.builtinDefine.apply(new SchemeCons(new SchemeSymbol("abc"), new SchemeString("Not a cons")),
                dummyEnvironment);
    }

    @Test
    public void testDoNotThrowErrorOnValidArgList() throws SchemeError {
        this.builtinDefine.apply(new SchemeCons(new SchemeSymbol("foobar"), new SchemeCons(new SchemeInteger(42), new
                SchemeNil())), dummyEnvironment);
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorIfConsCarIsNotSymbolOrCons() throws SchemeError {
        this.builtinDefine.apply(new SchemeCons(new SchemeInteger(42), new SchemeCons(new SchemeString("foobar"),
                new SchemeNil())), dummyEnvironment);
    }

    @Test
    public void testCreateVariableBindingThrowsErrorOnInvalidValueCons() {
        // Prepare arguments
        SchemeSymbol variableName = new SchemeSymbol("foobar");
        SchemeCons invalidValueList = new SchemeCons(new SchemeInteger(42), new SchemeCons(new SchemeString
                ("invalid"), new SchemeNil()));

        // Invoke method and catch exception
        try {
            ReflectionUtils.invokeMethod(this.builtinDefine, "createVariableBinding", new ReflectionCallArg(SchemeSymbol
                    .class, variableName), new ReflectionCallArg(SchemeCons.class, invalidValueList), new
                    ReflectionCallArg(Environment.class, dummyEnvironment));
            fail("Expected exception has not been thrown!");
        } catch (ReflectionMethodCallException e) {

            // Verify outcome by climbing up the exception chain
            assertThat(e.getCause().getCause().getClass(), equalTo(SchemeError.class));
            assertThat(e.getCause().getCause().getMessage(), equalTo("(define): bad syntax (multiple expressions " +
                    "after identifier)"));
        }
    }

    @Test
    public void testCreateVariableBindingReturnsVoidAndInvokesEnvironmentCorrectly() throws SchemeError {
        // Prepare invocation arguments
        SchemeSymbol variableName = new SchemeSymbol("foobar");
        SchemeCons valueCons = new SchemeCons(new SchemeInteger(42), new SchemeNil());
        Environment environmentMock = mock(Environment.class);

        // Prepare SchemeEval mock
        SchemeEval schemeEvalMock = mock(SchemeEval.class);
        when(schemeEvalMock.eval(new SchemeInteger(42), environmentMock)).thenReturn(new SchemeInteger(42));
        PowerMockito.mockStatic(SchemeEval.class);
        PowerMockito.when(SchemeEval.getInstance()).thenReturn(schemeEvalMock);

        // Call method
        SchemeVoid result = (SchemeVoid) ReflectionUtils.invokeMethod(this.builtinDefine, "createVariableBinding", new
                ReflectionCallArg(SchemeSymbol
                .class, variableName), new ReflectionCallArg(SchemeCons.class, valueCons), new ReflectionCallArg
                (Environment.class, environmentMock));

        // Verify outcome
        verify(environmentMock, times(1)).add(EnvironmentEntry.create(variableName, new SchemeInteger(42)));
        assertThat(result, notNullValue());
    }
}