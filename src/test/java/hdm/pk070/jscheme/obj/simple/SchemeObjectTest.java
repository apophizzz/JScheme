package hdm.pk070.jscheme.obj.simple;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.simple.SchemeInteger;
import hdm.pk070.jscheme.obj.simple.SchemeString;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author patrick.kleindienst
 */

public class SchemeObjectTest {

    private SchemeObject schemeIntObj;
    private SchemeObject schemeStringObj;


    @Before
    public void setUp() {
        this.schemeIntObj = new SchemeInteger(42);
        this.schemeStringObj = new SchemeString("foobar");
    }

    @Test
    public void testCreateSchemeIntegerIsValid() {

        assertThat(schemeIntObj, notNullValue());
        assertThat(schemeIntObj.typeOf(SchemeInteger.class), equalTo(true));
        assertThat(((SchemeInteger) schemeIntObj).getValue(), equalTo(42));
    }

    @Test
    public void testCreateSchemeStringIsValid() {

        assertThat(schemeStringObj, notNullValue());
        assertThat(schemeStringObj.typeOf(SchemeString.class), equalTo(true));
        assertThat(((SchemeString) schemeStringObj).getValue(), equalTo("foobar"));
    }


    @Test
    public void testSchemeIntAndSchemeStringHaveDifferentTypes() {

        assertThat(schemeIntObj.typeOf(SchemeString.class), equalTo(false));
        assertThat(schemeStringObj.typeOf(SchemeInteger.class), equalTo(false));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testTypeOfThrowsExceptionOnNullInput() {
        schemeStringObj.typeOf(null);
    }


    @Test
    public void testObjectsMustHaveSameTypeAndValueForEquality() {
        assertThat(schemeStringObj.equals(schemeIntObj), equalTo(false));
        assertThat(schemeIntObj.equals(new SchemeInteger((Integer) schemeIntObj.getValue())), equalTo(true));
        assertThat(schemeStringObj.equals(new SchemeString((String) schemeStringObj.getValue())), equalTo(true));
    }
}
