package hdm.pk070.jscheme.obj.builtin.simple;

import org.apache.commons.math3.fraction.Fraction;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * A test class for learning how to deal with Fractions.
 *
 * @author patrick.kleindienst
 */
public class ApacheCommonsFractionLearningTest {

    @Test
    public void testFraction() {
        Fraction fraction = new Fraction(5);

        assertThat(fraction.toString(), equalTo("5"));
    }

    @Test
    public void testAddFractions() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(1, 2);
        Fraction result = fraction1.add(fraction2);

        assertThat(result.toString(), equalTo("1"));
    }

    @Test
    public void testMultiplyFractions() {
        Fraction fraction1 = new Fraction(1, 2);
        Fraction fraction2 = new Fraction(1, 2);
        Fraction result = fraction1.multiply(fraction2);

        assertThat(result.toString().replace(" ", ""), equalTo("1/4"));
    }

    @Test
    public void testCompareFractions() {
        Fraction fraction1 = new Fraction(0);
        Fraction fraction2 = new Fraction(1);

        assertThat(fraction1.compareTo(fraction2), equalTo(-1));
        assertThat(fraction2.compareTo(fraction1), equalTo(1));
    }
}
