package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.SchemeEval;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeFalse;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeTrue;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import hdm.pk070.jscheme.table.environment.LocalEnvironment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.*;

/**
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest({SchemeEval.class})
public class SchemeBuiltinIfTest {

    private SchemeBuiltinIf schemeBuiltinIf;

    private LocalEnvironment dummyEnvironment;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.schemeBuiltinIf = SchemeBuiltinIf.create();
        this.dummyEnvironment = LocalEnvironment.withSize(42);
    }

    @Test
    public void testApplyThrowsErrorOnEmptyArgumentList() throws SchemeError {
        expectedException.expect(SchemeError.class);
        expectedException.expectMessage("(if): bad syntax, has 0 parts after keyword in: (if)");

        SchemeObject emptyArgList = new SchemeNil();
        this.schemeBuiltinIf.apply(emptyArgList, null);
    }

    @Test
    public void testApplyThrowsErrorOnSingleArgument() throws SchemeError {
        expectedException.expect(SchemeError.class);
        expectedException.expectMessage("(if): bad syntax, has 1 parts after keyword in: (if 42)");

        SchemeCons incompleteArgList = new SchemeCons(new SchemeInteger(42), new SchemeNil());
        this.schemeBuiltinIf.apply(incompleteArgList, null);
    }

    @Test
    public void testApplyThrowsErrorOnTwoArguments() throws SchemeError {
        expectedException.expect(SchemeError.class);
        expectedException.expectMessage("(if): missing 'else' expression in: (if 42 43)");

        SchemeCons incompleteArgList = new SchemeCons(new SchemeInteger(42), new SchemeCons(new SchemeInteger(43),
                new SchemeNil()));
        this.schemeBuiltinIf.apply(incompleteArgList, null);
    }

    @Test
    public void testApplyThrowsErrorOnToManyArguments() throws SchemeError {
        expectedException.expect(SchemeError.class);
        expectedException.expectMessage("(if): bad syntax, has too many parts after keyword in: (if 42 43 44 45)");

        SchemeCons invalidArgList = new SchemeCons(new SchemeInteger(42), new SchemeCons(new SchemeInteger(43),
                new SchemeCons(new SchemeInteger(44), new SchemeCons(new SchemeInteger(45), new SchemeNil()))));
        this.schemeBuiltinIf.apply(invalidArgList, null);
    }

    @Test
    public void testApplyEvaluatesTrueExpressionOnNonZeroNumber() throws SchemeError {
        SchemeCons expression = new SchemeCons(new SchemeInteger(1), new SchemeCons(new SchemeInteger(42), new
                SchemeCons(new SchemeString("wrong result"), new SchemeNil())));

        SchemeEval schemeEvalMock = mock(SchemeEval.class);
        when(schemeEvalMock.eval(new SchemeInteger(1), dummyEnvironment)).thenReturn(new SchemeInteger(1));

        PowerMockito.mockStatic(SchemeEval.class);
        PowerMockito.when(SchemeEval.getInstance()).thenReturn(schemeEvalMock);

        this.schemeBuiltinIf.apply(expression, dummyEnvironment);

        verify(schemeEvalMock).eval(new SchemeInteger(42), dummyEnvironment);
    }

    @Test
    public void testApplyEvaluatesFalseExpressionOnNumericalZero() throws SchemeError {
        SchemeCons expression = new SchemeCons(new SchemeFloat(0.0f), new SchemeCons(new SchemeString("wrong result")
                , new SchemeCons(new SchemeInteger(42), new SchemeNil())));

        SchemeEval schemeEvalMock = mock(SchemeEval.class);
        when(schemeEvalMock.eval(new SchemeFloat(0.0f), dummyEnvironment)).thenReturn(new SchemeFloat(0.0f));

        PowerMockito.mockStatic(SchemeEval.class);
        PowerMockito.when(SchemeEval.getInstance()).thenReturn(schemeEvalMock);

        this.schemeBuiltinIf.apply(expression, dummyEnvironment);

        verify(schemeEvalMock).eval(new SchemeFloat(0.0f), dummyEnvironment);
        verify(schemeEvalMock).eval(new SchemeInteger(42), dummyEnvironment);
    }

    @Test
    public void testApplyEvaluatesTrueExpressionOnNonEmptyString() throws SchemeError {
        SchemeCons expression = new SchemeCons(new SchemeString("I am true"), new SchemeCons(new SchemeInteger(42)
                , new SchemeCons(new SchemeString("wrong result"), new SchemeNil())));

        SchemeEval schemeEvalMock = mock(SchemeEval.class);
        when(schemeEvalMock.eval(new SchemeString("I am true"), dummyEnvironment)).thenReturn(new SchemeString("I am " +
                "true"));

        PowerMockito.mockStatic(SchemeEval.class);
        PowerMockito.when(SchemeEval.getInstance()).thenReturn(schemeEvalMock);

        this.schemeBuiltinIf.apply(expression, dummyEnvironment);

        verify(schemeEvalMock).eval(new SchemeString("I am true"), dummyEnvironment);
        verify(schemeEvalMock).eval(new SchemeInteger(42), dummyEnvironment);
    }

    @Test
    public void testApplyEvaluatesFalseExpressionOnEmptyString() throws SchemeError {
        SchemeCons expression = new SchemeCons(new SchemeString(""), new SchemeCons(new SchemeString("wrong result")
                , new SchemeCons(new SchemeInteger(42), new SchemeNil())));
        SchemeEval schemeEvalMock = mock(SchemeEval.class);
        when(schemeEvalMock.eval(new SchemeString(""), dummyEnvironment)).thenReturn(new SchemeString(""));

        PowerMockito.mockStatic(SchemeEval.class);
        PowerMockito.when(SchemeEval.getInstance()).thenReturn(schemeEvalMock);

        this.schemeBuiltinIf.apply(expression, dummyEnvironment);

        verify(schemeEvalMock).eval(new SchemeString(""), dummyEnvironment);
        verify(schemeEvalMock).eval(new SchemeInteger(42), dummyEnvironment);
    }

    @Test
    public void testApplyEvaluatesFalseExpressionOnNull() throws SchemeError {
        SchemeCons expression = new SchemeCons(new SchemeNil(), new SchemeCons(new SchemeString("wrong result")
                , new SchemeCons(new SchemeInteger(42), new SchemeNil())));
        SchemeEval schemeEvalMock = mock(SchemeEval.class);
        when(schemeEvalMock.eval(new SchemeNil(), dummyEnvironment)).thenReturn(new SchemeNil());

        PowerMockito.mockStatic(SchemeEval.class);
        PowerMockito.when(SchemeEval.getInstance()).thenReturn(schemeEvalMock);

        this.schemeBuiltinIf.apply(expression, dummyEnvironment);

        verify(schemeEvalMock).eval(new SchemeNil(), dummyEnvironment);
        verify(schemeEvalMock).eval(new SchemeInteger(42), dummyEnvironment);
    }

    @Test
    public void testApplyEvaluatesTrueExpressionOnSchemeTrue() throws SchemeError {
        SchemeCons expression = new SchemeCons(new SchemeTrue(), new SchemeCons(new SchemeInteger(42)
                , new SchemeCons(new SchemeString("wrong result"), new SchemeNil())));

        SchemeEval schemeEvalMock = mock(SchemeEval.class);
        when(schemeEvalMock.eval(new SchemeTrue(), dummyEnvironment)).thenReturn(new SchemeTrue());

        PowerMockito.mockStatic(SchemeEval.class);
        PowerMockito.when(SchemeEval.getInstance()).thenReturn(schemeEvalMock);

        this.schemeBuiltinIf.apply(expression, dummyEnvironment);

        verify(schemeEvalMock).eval(new SchemeTrue(), dummyEnvironment);
        verify(schemeEvalMock).eval(new SchemeInteger(42), dummyEnvironment);
    }

    @Test
    public void testApplyEvaluatesFalseExpressionOnSchemeFalse() throws SchemeError {
        SchemeCons expression = new SchemeCons(new SchemeFalse(), new SchemeCons(new SchemeString("wrong result")
                , new SchemeCons(new SchemeInteger(42), new SchemeNil())));

        SchemeEval schemeEvalMock = mock(SchemeEval.class);
        when(schemeEvalMock.eval(new SchemeFalse(), dummyEnvironment)).thenReturn(new SchemeFalse());

        PowerMockito.mockStatic(SchemeEval.class);
        PowerMockito.when(SchemeEval.getInstance()).thenReturn(schemeEvalMock);

        this.schemeBuiltinIf.apply(expression, dummyEnvironment);

        verify(schemeEvalMock).eval(new SchemeFalse(), dummyEnvironment);
        verify(schemeEvalMock).eval(new SchemeInteger(42), dummyEnvironment);
    }
}