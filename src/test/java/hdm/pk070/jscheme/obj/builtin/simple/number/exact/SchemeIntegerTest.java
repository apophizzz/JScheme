package hdm.pk070.jscheme.obj.builtin.simple.number.exact;

import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author patrick.kleindienst
 */
public class SchemeIntegerTest {

    private SchemeInteger integer;


    @Before
    public void setUp() {
        this.integer = new SchemeInteger(42);
    }

    @Test
    public void testAddIntegerToInteger() {
        SchemeNumber result = this.integer.add(new SchemeInteger(42));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeInteger.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(84));
    }

    @Test
    public void testAddFloatToInteger() {
        SchemeNumber result = this.integer.add(new SchemeFloat(42.0f));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(84.0f));
    }
}
