package hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * @author patrick.kleindienst
 */
public class SchemeFloatTest {

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
}
