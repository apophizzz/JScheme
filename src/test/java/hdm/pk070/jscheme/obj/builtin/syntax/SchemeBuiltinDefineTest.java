package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.SchemeEval;
import hdm.pk070.jscheme.obj.builtin.simple.*;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunction;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.LocalEnvironment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import hdm.pk070.jscheme.util.ReflectionCallArg;
import hdm.pk070.jscheme.util.ReflectionUtils;
import hdm.pk070.jscheme.util.exception.ReflectionMethodCallException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest({SchemeEval.class, EnvironmentEntry.class, SchemeCustomUserFunction.class})
public class SchemeBuiltinDefineTest {

    private SchemeBuiltinSyntax builtinDefine;
    private Environment dummyEnvironment;
    private Environment environmentMock;

    private SchemeCons validFunctionSignature;
    private SchemeCons invalidFunctionSignature;
    private SchemeCons validFunctionBody;
    private SchemeCons invalidFunctionBody;


    @Before
    public void setUp() {
        this.builtinDefine = SchemeBuiltinDefine.create();
        this.dummyEnvironment = LocalEnvironment.withSize(42);
        this.environmentMock = mock(Environment.class);

        this.invalidFunctionSignature = new SchemeCons(new SchemeString("Invalid name"), new SchemeCons(new
                SchemeSymbol("x"), new SchemeNil()));
        this.validFunctionSignature = new SchemeCons(new SchemeSymbol("functionName"), new SchemeSymbol("x"));
        this.invalidFunctionBody = new SchemeCons(new SchemeNil(), new SchemeNil());
        this.validFunctionBody = new SchemeCons(new SchemeInteger(42), new SchemeNil());
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
            failOnMissingException();
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

        // Prepare SchemeEval mock
        SchemeEval schemeEvalMock = mock(SchemeEval.class);
        when(schemeEvalMock.eval(new SchemeInteger(42), this.environmentMock)).thenReturn(new SchemeInteger(42));
        PowerMockito.mockStatic(SchemeEval.class);
        PowerMockito.when(SchemeEval.getInstance()).thenReturn(schemeEvalMock);

        // Call method
        SchemeVoid result = (SchemeVoid) ReflectionUtils.invokeMethod(this.builtinDefine, "createVariableBinding", new
                ReflectionCallArg(SchemeSymbol.class, variableName), new ReflectionCallArg(SchemeCons.class,
                valueCons), new ReflectionCallArg(Environment.class, this.environmentMock));

        // Verify outcome
        verify(this.environmentMock, times(1)).add(EnvironmentEntry.create(variableName, new SchemeInteger(42)));
        assertThat(result, notNullValue());
    }

    @Test
    public void testCreateFunctionBindingThrowsErrorOnInvalidName() {
        try {
            ReflectionUtils.invokeMethod(this.builtinDefine, "createFunctionBinding", new ReflectionCallArg(SchemeCons
                    .class, this.invalidFunctionSignature), new ReflectionCallArg(SchemeCons.class, null), new
                    ReflectionCallArg
                    (Environment.class, null));
            failOnMissingException();
        } catch (ReflectionMethodCallException e) {
            assertThat(e.getCause().getCause().getClass(), equalTo(SchemeError.class));
            assertThat(e.getCause().getCause().getMessage(), equalTo("(define): bad syntax (not an identifier for " +
                    "procedure name: \"Invalid name\")"));
        }
    }


    @Test
    public void testCreateFunctionBindingReturnsVoidOnValidName() throws SchemeError {
        when(this.environmentMock.add(null)).thenReturn(null);

        PowerMockito.mockStatic(EnvironmentEntry.class);
        PowerMockito.when(EnvironmentEntry.create(any(), any(SchemeCustomUserFunction.class))).thenReturn(null);

        Object result = ReflectionUtils.invokeMethod(this.builtinDefine, "createFunctionBinding", new
                ReflectionCallArg(SchemeCons.class, this.validFunctionSignature), new ReflectionCallArg(SchemeCons
                .class, this.validFunctionBody), new ReflectionCallArg(Environment.class, this.environmentMock));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.getClass(), equalTo(SchemeVoid.class));
    }

    @Test
    public void testCreateFunctionBindingInvokesEnvironmentCorrectly() throws SchemeError {
        SchemeCustomUserFunction customUserFunctionDummy = SchemeCustomUserFunction.create(((SchemeSymbol)
                this.validFunctionSignature.getCar()).getValue(), this.validFunctionSignature.getCdr(), this
                .validFunctionBody, this.environmentMock);
        PowerMockito.mockStatic(SchemeCustomUserFunction.class);
        PowerMockito.when(SchemeCustomUserFunction.create(((SchemeSymbol) this.validFunctionSignature.getCar())
                .getValue(), this.validFunctionSignature.getCdr(), this.validFunctionBody, this.environmentMock))
                .thenReturn(customUserFunctionDummy);

        ReflectionUtils.invokeMethod(this.builtinDefine, "createFunctionBinding", new
                ReflectionCallArg(this.validFunctionSignature), new ReflectionCallArg(SchemeCons.class,
                this.validFunctionBody), new ReflectionCallArg(Environment.class, this.environmentMock));

        verify(this.environmentMock, times(1)).add(EnvironmentEntry.create((SchemeSymbol) this.validFunctionSignature
                .getCar(), customUserFunctionDummy));
    }

    @Test
    public void testThrowErrorOnEmptyFunctionBodyList() {
        try {
            ReflectionUtils.invokeMethod(this.builtinDefine, "createFunctionBinding", new ReflectionCallArg
                    (this.validFunctionSignature), new ReflectionCallArg(this.invalidFunctionBody), new
                    ReflectionCallArg(Environment.class, this.dummyEnvironment));
            failOnMissingException();
        } catch (ReflectionMethodCallException e) {
            assertThat(e.getCause().getCause().getClass(), equalTo(SchemeError.class));
            assertThat(e.getCause().getCause().getMessage(), equalTo("(define): missing procedure expression"));
        }
    }

    @Test
    public void testThrowErrorOnLastPartOfBodyListIsNoExpression() {
        SchemeCons bodyWithoutExpression = new SchemeCons(new SchemeCons(new SchemeSymbol("define"), new SchemeCons
                (new SchemeSymbol("x"), new SchemeCons(new SchemeInteger(42), new SchemeNil()))), new SchemeNil());

        try {
            ReflectionUtils.invokeMethod(this.builtinDefine, "createFunctionBinding", new ReflectionCallArg
                    (this.validFunctionSignature), new ReflectionCallArg(bodyWithoutExpression), new
                    ReflectionCallArg(Environment.class, this.dummyEnvironment));
            failOnMissingException();
        } catch (ReflectionMethodCallException e) {
            assertThat(e.getCause().getCause().getClass(), equalTo(SchemeError.class));
            assertThat(e.getCause().getCause().getMessage(), equalTo("(define): no expression after sequence of " +
                    "internal definitions"));
        }
    }

    private void failOnMissingException() {
        fail("Expected exception has not been thrown!");
    }
}