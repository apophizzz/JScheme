package hdm.pk070.jscheme.obj.builtin.simple.number.exact;

import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import org.apache.commons.math3.fraction.Fraction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by patrick on 01.07.16.
 */
public class SchemeFractionTest {

    private SchemeFraction schemeFraction;

    @Before
    public void setUp() {
        this.schemeFraction = new SchemeFraction(1, 3);
    }

    @Test
    public void testAddIntegerToSchemeFraction() {
        SchemeNumber result = this.schemeFraction.add(new SchemeInteger(4));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFraction.class), equalTo(true));
        assertThat("Result does not match expected value!", result.equals(new SchemeFraction(13, 3)), equalTo(true));
    }

    @Test
    public void testAddFloatToSchemeFraction() {
        SchemeNumber result = this.schemeFraction.add(new SchemeFloat(4.0f));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result, equalTo(new SchemeFloat(new Fraction(13, 3)
                .floatValue())));
    }

    @Test
    public void testAddFractionToSchemeFraction() {
        SchemeNumber result = this.schemeFraction.add(new SchemeFraction(1, 2));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFraction.class), equalTo(true));
        assertThat("Result does not match expected value!", result, equalTo(new SchemeFraction(5, 6)));
    }
}