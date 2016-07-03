package hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex;

import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeFraction;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author patrick.kleindienst
 */
public class SchemeFloatTest {

    private SchemeFloat schemeFloat;

    @Before
    public void setUp() {
        this.schemeFloat = new SchemeFloat(42.0f);
    }

    @Test
    public void testValueWithoutLeadingZero() {
        SchemeFloat schemeFloat = new SchemeFloat(.5f);

        assertThat(schemeFloat.getValue(), equalTo(0.5f));
    }

    @Test
    public void testValueWithoutDecimalPlace() {
        SchemeFloat schemeFloat = new SchemeFloat(5.f);

        assertThat(schemeFloat.getValue(), equalTo(5.0f));
    }

    @Test
    public void testAddFloatToSchemeFloat() {
        SchemeNumber result = this.schemeFloat.add(new SchemeFloat(42.0f));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(84.0f));
    }

    @Test
    public void testAddIntegerToSchemeFloat() {
        SchemeNumber result = this.schemeFloat.add(new SchemeInteger(42));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(84.0f));
    }

    @Test
    public void testAddFractionToSchemeFloat() {
        SchemeNumber result = this.schemeFloat.add(new SchemeFraction(3, 4));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(42.75f));
    }

    @Test
    public void testMultiplySchemeFloatByFloat() {
        SchemeNumber result = this.schemeFloat.multiply(new SchemeFloat(5.0f));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(210.0f));
    }

    @Test
    public void testMultiplySchemeFloatByInteger() {
        SchemeNumber result = this.schemeFloat.multiply(new SchemeInteger(3));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(126.0f));
    }

    @Test
    public void testMultiplySchemeFloatByFraction() {
        SchemeNumber result = this.schemeFloat.multiply(new SchemeFraction(1, 3));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(14.0f));
    }

    @Test
    public void testSubtractFloatFromSchemeFloat() {
        SchemeNumber result = this.schemeFloat.subtract(new SchemeFloat(12.0f));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(30.0f));
    }

    @Test
    public void testSubtractIntegerFromSchemeFloat() {
        SchemeNumber result = this.schemeFloat.subtract(new SchemeInteger(12));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(30.0f));
    }

    @Test
    public void testSubtractFractionFromSchemeFloat() {
        SchemeNumber result = this.schemeFloat.subtract(new SchemeFraction(1, 2));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(41.5f));
    }

    @Test
    public void testDivideSchemeFloatByFloat() {
        SchemeNumber result = this.schemeFloat.divide(new SchemeFloat(2.0f));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(21.0f));
    }

    @Test
    public void testDivideSchemeFloatByInteger() {
        SchemeNumber result = this.schemeFloat.divide(new SchemeInteger(2));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(21.0f));
    }

    @Test
    public void testDivideSchemeFloatByFraction() {
        SchemeNumber result = this.schemeFloat.divide(new SchemeFraction(2));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(21.0f));
    }

    @Test
    public void testReturnAbsoluteOnPositiveValue() {
        SchemeNumber result = this.schemeFloat.absolute();

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(42.0f));
    }

    @Test
    public void testReturnAbsoluteOnNegativeValue() {
        SchemeNumber result = this.schemeFloat.multiply(new SchemeInteger(-1)).absolute();

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(42.0f));
    }
}
