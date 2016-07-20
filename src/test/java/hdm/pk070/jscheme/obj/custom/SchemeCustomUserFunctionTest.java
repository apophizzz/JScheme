package hdm.pk070.jscheme.obj.custom;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.util.ReflectionCallArg;
import hdm.pk070.jscheme.util.ReflectionUtils;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author patrick.kleindienst
 */
public class SchemeCustomUserFunctionTest {

    private SchemeCustomUserFunction customUserFunction;


    @Test
    public void testCountSingleDefinition() {
        SchemeCons bodyListWithSingleDefine = new SchemeCons(new SchemeCons(new SchemeSymbol("define"), new
                SchemeCons(new
                SchemeSymbol("a"), new SchemeCons(new SchemeInteger(42), new SchemeNil()))), new SchemeCons(new
                SchemeCons(new SchemeSymbol("+"), new SchemeCons(new SchemeInteger(1), new SchemeCons(new
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

    private void checkResult(Object actual, Object expected) {
        assertThat("Result must not be null!", actual, notNullValue());
        assertThat(String.format("Result must be of type %s!", expected.getClass().getSimpleName()),
                actual.getClass(), equalTo(expected.getClass()));
        assertThat("Result does not match expected value!", actual, equalTo(expected));
    }

}