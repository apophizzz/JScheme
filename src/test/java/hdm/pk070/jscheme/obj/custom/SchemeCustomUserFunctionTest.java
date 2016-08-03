package hdm.pk070.jscheme.obj.custom;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.util.ReflectionCallArg;
import hdm.pk070.jscheme.util.ReflectionUtils;
import hdm.pk070.jscheme.util.exception.ReflectionMethodCallException;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * A test class for {@link SchemeCustomUserFunction}
 *
 * @author patrick.kleindienst
 */
public class SchemeCustomUserFunctionTest {

    private SchemeCustomUserFunction customUserFunction;


    @Test
    public void testCountSingleDefinition() {
        SchemeCons bodyListWithSingleDefine = new SchemeCons(new SchemeCons(new SchemeSymbol("define"), new
                SchemeCons(new SchemeSymbol("a"), new SchemeCons(new SchemeInteger(42), new SchemeNil()))), new
                SchemeCons(new SchemeCons(new SchemeSymbol("+"), new SchemeCons(new SchemeInteger(1), new SchemeCons(new
                SchemeSymbol("x"), new SchemeNil()))), new SchemeNil()));

        this.customUserFunction = SchemeCustomUserFunction.create("testFunc", null,
                bodyListWithSingleDefine, null);

        Object result = ReflectionUtils.invokeMethod(this.customUserFunction, "countDefinitions");

        checkResult(result, 1);
    }

    @Test
    public void testCountTwoDefinitions() {
        SchemeCons bodyListWithDefines = new SchemeCons(new SchemeCons(new SchemeSymbol("define"), new SchemeCons(new
                SchemeSymbol("x"), new SchemeCons(new SchemeInteger(1), new SchemeNil())))
                , new SchemeCons(new SchemeCons(new SchemeSymbol("define"), new SchemeCons(new SchemeSymbol("y"), new
                SchemeCons(new SchemeInteger(2), new SchemeNil()))), new SchemeCons(new SchemeCons(new SchemeSymbol
                ("+"), new SchemeCons(new SchemeSymbol("x"), new SchemeCons(new SchemeSymbol("y"), new SchemeNil())))
                , new SchemeNil())));

        this.customUserFunction = SchemeCustomUserFunction.create("testFunc", null,
                bodyListWithDefines, null);

        Object result = ReflectionUtils.invokeMethod(this.customUserFunction, "countDefinitions");

        checkResult(result, 2);
    }

    @Test
    public void testCountZeroDefinitions() {
        SchemeCons bodyListWithNoDefine = new SchemeCons(new SchemeInteger(42), new SchemeNil());

        this.customUserFunction = SchemeCustomUserFunction.create("testFunc", null,
                bodyListWithNoDefine, null);

        Object result = ReflectionUtils.invokeMethod(this.customUserFunction, "countDefinitions");

        checkResult(result, 0);
    }

    @Test
    public void testIsDefinitionReturnsFalseNonListArgument() {
        this.customUserFunction = SchemeCustomUserFunction.create("testFunc", null, null, null);
        SchemeString nonListArgument = new SchemeString("Not a list");

        Object result = ReflectionUtils.invokeMethod(this.customUserFunction, "isDefinition", new ReflectionCallArg
                (SchemeObject.class, nonListArgument));

        checkResult(result, false);
    }

    @Test
    public void testIsDefinitionReturnsFalseOnNonDefinitionList() {
        this.customUserFunction = SchemeCustomUserFunction.create("testFunc", null, null, null);
        SchemeCons nonDefineBodyList = new SchemeCons(new SchemeSymbol("+"), new SchemeCons(new SchemeInteger(42),
                new SchemeNil()));

        Object result = ReflectionUtils.invokeMethod(this.customUserFunction, "isDefinition", new
                ReflectionCallArg(SchemeObject.class, nonDefineBodyList));

        checkResult(result, false);
    }

    @Test
    public void testIsDefinitionReturnsTrueOnDefinitionList() {
        this.customUserFunction = SchemeCustomUserFunction.create("testFunc", null, null, null);
        SchemeCons defineBodyList = new SchemeCons(new SchemeSymbol("define"),
                new SchemeCons(new SchemeSymbol("abc"), new SchemeCons(new SchemeInteger(42), new SchemeNil())));

        Object result = ReflectionUtils.invokeMethod(this.customUserFunction, "isDefinition", new
                ReflectionCallArg(SchemeObject.class, defineBodyList));

        checkResult(result, true);
    }

    @Test
    public void testCountZeroParameters() {
        this.customUserFunction = SchemeCustomUserFunction.create("testFunc", new SchemeNil(), null, null);

        Object result = ReflectionUtils.invokeMethod(this.customUserFunction, "countParams");

        checkResult(result, 0);
    }

    @Test
    public void testCountSingleParameter() {
        SchemeCons singleParam = new SchemeCons(new SchemeSymbol("x"), new SchemeNil());
        this.customUserFunction = SchemeCustomUserFunction.create("testFunc", singleParam, null, null);

        Object result = ReflectionUtils.invokeMethod(this.customUserFunction, "countParams");

        checkResult(result, 1);
    }

    @Test
    public void testCountMultipleParameters() {
        SchemeCons paramList = new SchemeCons(new SchemeSymbol("x"), new SchemeCons(new SchemeSymbol("y"), new
                SchemeNil()));
        this.customUserFunction = SchemeCustomUserFunction.create("testFunc", paramList, null, null);

        Object result = ReflectionUtils.invokeMethod(this.customUserFunction, "countParams");

        checkResult(result, 2);
    }

    @Test
    public void testIsValidParamOnSymbolInput() {
        SchemeCons singleParam = new SchemeCons(new SchemeSymbol("x"), new SchemeNil());
        this.customUserFunction = SchemeCustomUserFunction.create("testFunc", null, null, null);

        Object result = ReflectionUtils.invokeMethod(this.customUserFunction, "isValidParam", new
                ReflectionCallArg(SchemeObject
                .class, singleParam));

        checkResult(result, true);
    }

    @Test
    public void testIsValidParamOnListInput() {
        SchemeCons paramList = new SchemeCons(new SchemeSymbol("x"), new SchemeCons(new SchemeSymbol("y"), new
                SchemeNil()));
        this.customUserFunction = SchemeCustomUserFunction.create("testFunc", null, null, null);

        Object result = ReflectionUtils.invokeMethod(this.customUserFunction, "isValidParam", new
                ReflectionCallArg(SchemeObject
                .class, paramList));

        checkResult(result, true);
    }

    @Test
    public void testIsValidParamThrowsErrorOnInvalidInput() {
        SchemeCons invalidParam = new SchemeCons(new SchemeInteger(42), new SchemeNil());
        this.customUserFunction = SchemeCustomUserFunction.create("testFunc", null, null, null);

        try {
            ReflectionUtils.invokeMethod(this.customUserFunction, "isValidParam",
                    new ReflectionCallArg(SchemeObject.class, invalidParam));
            fail("Expected exception has not been thrown!");
        } catch (ReflectionMethodCallException e) {
            assertThat(e.getCause().getCause().getClass(), equalTo(SchemeError.class));
            assertThat(e.getCause().getCause().getMessage(), equalTo("(define): not an identifier for procedure " +
                    "argument in: 42"));
        }
    }

    private void checkResult(Object actual, Object expected) {
        assertThat("Result must not be null!", actual, notNullValue());
        assertThat(String.format("Result must be of type %s!", expected.getClass().getSimpleName()),
                actual.getClass(), equalTo(expected.getClass()));
        assertThat("Result does not match expected value!", actual, equalTo(expected));
    }

}