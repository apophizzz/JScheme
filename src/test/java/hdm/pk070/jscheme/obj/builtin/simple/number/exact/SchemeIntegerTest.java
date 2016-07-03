package hdm.pk070.jscheme.obj.builtin.simple.number.exact;

import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import org.apache.commons.math3.fraction.Fraction;
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
    public void testAddIntegerToSchemeInteger() {
        SchemeNumber result = this.integer.add(new SchemeInteger(42));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeInteger.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(84));
    }

    @Test
    public void testAddFloatToSchemeInteger() {
        SchemeNumber result = this.integer.add(new SchemeFloat(42.0f));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(84.0f));
    }

    @Test
    public void testAddFractionToSchemeInteger() {
        SchemeNumber result = this.integer.add(new SchemeFraction(1, 3));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFraction.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(new Fraction(127, 3)));
    }

    @Test
    public void testMultiplySchemeIntegerByInteger() {
        SchemeNumber result = this.integer.multiply(new SchemeInteger(3));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeInteger.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(126));
    }

    @Test
    public void testMultiplySchemeIntegerByFloat() {
        SchemeNumber result = this.integer.multiply(new SchemeFloat(3.0f));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(126.0f));
    }

    @Test
    public void testMultiplySchemeIntegerByFraction() {
        SchemeNumber result = this.integer.multiply(new SchemeFraction(1, 7));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFraction.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(new Fraction(42, 7)));
    }

    @Test
    public void testSubtractIntegerFromSchemeInteger() {
        SchemeNumber result = this.integer.subtract(new SchemeInteger(30));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeInteger.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(12));
    }

    @Test
    public void testSubtractFloatFromSchemeInteger() {
        SchemeNumber result = this.integer.subtract(new SchemeFloat(30.0f));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(12.0f));
    }

    @Test
    public void testSubtractFractionFromSchemeInteger() {
        SchemeNumber result = this.integer.subtract(new SchemeFraction(3, 4));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFraction.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(new Fraction(165, 4)));
    }

    @Test
    public void testDivideSchemeIntegerByInteger() {
        SchemeNumber result = this.integer.divide(new SchemeInteger(2));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFraction.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(new Fraction(21)));
    }

    @Test
    public void testDivideSchemeIntegerByFloat() {
        SchemeNumber result = this.integer.divide(new SchemeFloat(2.0f));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(21.0f));
    }

    @Test
    public void testDivideSchemeIntegerByFraction() {
        SchemeNumber result = this.integer.divide(new SchemeFraction(5));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFraction.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(new Fraction(42, 5)));
    }
}
