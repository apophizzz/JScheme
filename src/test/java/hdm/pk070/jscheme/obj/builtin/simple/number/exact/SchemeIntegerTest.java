package hdm.pk070.jscheme.obj.builtin.simple.number.exact;

import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * A test class for {@link SchemeInteger}
 *
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

    @Test
    public void testMultiplyIntegerByInteger() {
        SchemeNumber result = this.integer.multiply(new SchemeInteger(3));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeInteger.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(126));
    }

    @Test
    public void testMultiplyIntegerByFloat() {
        SchemeNumber result = this.integer.multiply(new SchemeFloat(3.0f));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(126.0f));
    }

    @Test
    public void testSubtractIntegerFromInteger() {
        SchemeNumber result = this.integer.subtract(new SchemeInteger(30));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeInteger.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(12));
    }

    @Test
    public void testSubtractFloatFromInteger() {
        SchemeNumber result = this.integer.subtract(new SchemeFloat(30.0f));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(12.0f));
    }
}
