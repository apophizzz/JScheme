package hdm.pk070.jscheme.obj;

import hdm.pk070.jscheme.obj.tag.Tag;
import hdm.pk070.jscheme.obj.type.SchemeInteger;
import hdm.pk070.jscheme.obj.type.SchemeString;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by patrick on 19.04.16.
 */

public class SchemeObjectTest {

    private SchemeObject schemeIntObj;
    private SchemeObject schemeStringObj;


    @Before
    public void setUp() {
        this.schemeIntObj = SchemeInteger.createObj(42);
        this.schemeStringObj = SchemeString.createObj("foobar");
    }

    @Test
    public void testCreateSchemeIntegerIsValid() {

        assertThat(schemeIntObj, notNullValue());
        assertThat(schemeIntObj.getTag(), notNullValue());
        assertThat(schemeIntObj.getTag(), equalTo(Tag.T_INTEGER));
        assertThat(schemeIntObj.typeOf(SchemeInteger.class), equalTo(true));
        assertThat(((SchemeInteger) schemeIntObj).getIntVal(), equalTo(42));
    }

    @Test
    public void testCreateSchemeStringIsValid() {

        assertThat(schemeStringObj, notNullValue());
        assertThat(schemeStringObj.getTag(), notNullValue());
        assertThat(schemeStringObj.getTag(), equalTo(Tag.T_STRING));
        assertThat(schemeStringObj.typeOf(SchemeString.class), equalTo(true));
        assertThat(((SchemeString) schemeStringObj).getStringVal(), equalTo("foobar"));
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
}
