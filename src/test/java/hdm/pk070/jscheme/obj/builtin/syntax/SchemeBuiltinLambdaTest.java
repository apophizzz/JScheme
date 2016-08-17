package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunction;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author patrick.kleindienst
 */
public class SchemeBuiltinLambdaTest {

    private SchemeBuiltinLambda builtinLambda;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.builtinLambda = SchemeBuiltinLambda.create();
    }

    @Test
    public void testApplyThrowsErrorOnMissingLambdaArguments() throws SchemeError {
        this.expectedException.expect(SchemeError.class);
        this.expectedException.expectMessage("(lambda): bad syntax in: (lambda) [expected 2 arguments, 0 given]");

        SchemeObject emptyArgumentList = new SchemeNil();
        this.builtinLambda.apply(emptyArgumentList, null);
    }

    @Test
    public void testApplyThrowsErrorIfParameterListIsNotNilOrListOrSymbol() throws SchemeError {
        this.expectedException.expect(SchemeError.class);
        this.expectedException.expectMessage("(lambda): bad syntax, invalid parameter list");

        SchemeObject invalidParamList = new SchemeCons(new SchemeInteger(42), new SchemeNil());
        this.builtinLambda.apply(invalidParamList, null);
    }


    @Test
    public void testApplyThrowsErrorOnMissingBodyList() throws SchemeError {
        this.expectedException.expect(SchemeError.class);
        this.expectedException.expectMessage("(lambda): bad syntax in: (lambda (x)) [expected 2 arguments, 1 given]");

        SchemeCons missingBodyArgList = new SchemeCons(new SchemeCons(new SchemeSymbol("x"), new SchemeNil()), new
                SchemeNil());
        this.builtinLambda.apply(missingBodyArgList, null);
    }

    @Test
    public void testApplyReturnsValidCustomFunction() throws SchemeError {
        SchemeCons paramList = (new SchemeCons(new SchemeSymbol("x"), new SchemeNil()));
        SchemeCons bodyList = new SchemeCons(new SchemeCons(new SchemeSymbol("+"), new SchemeCons(new SchemeSymbol
                ("x"), new SchemeCons(new SchemeInteger(1), new SchemeNil()))), new SchemeNil());
        SchemeCons validLambdaArgumentList = new SchemeCons(paramList, bodyList);

        SchemeObject result = this.builtinLambda.apply(validLambdaArgumentList, null);

        assertThat("Result may not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.getClass(), equalTo(SchemeCustomUserFunction.class));
        assertThat("Custom function does not have expected param count!", ((SchemeCustomUserFunction) result)
                .getParamCount(), equalTo(1));
        assertThat("Custom function does not have expected param list!", ((SchemeCustomUserFunction) result)
                .getParameterList(), equalTo(paramList));
        assertThat("Custom function does not have expected body list!", ((SchemeCustomUserFunction) result)
                .getFunctionBodyList(), equalTo(bodyList));
    }
}