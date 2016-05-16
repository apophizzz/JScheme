package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.obj.type.SchemeInteger;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by patrick on 16.05.16.
 */
public class EnvironmentEntryTest {


    private EnvironmentEntry environmentEntryA;
    private EnvironmentEntry environmentEntryB;
    private EnvironmentEntry environmentEntryC;

    @Before
    public void setUp() {
        SchemeSymbol keySymbol = new SchemeSymbol("foo");
        SchemeSymbol anotherKeySymbol = new SchemeSymbol("bar");

        environmentEntryA = EnvironmentEntry.create(keySymbol, new SchemeInteger(42));
        environmentEntryB = EnvironmentEntry.create(keySymbol, new SchemeInteger(43));
        environmentEntryC = EnvironmentEntry.create(anotherKeySymbol, new SchemeInteger(99));
    }

    @Test
    public void testEquals() {
        assertThat("entries must be equal!", environmentEntryA.equals(environmentEntryB), equalTo(true));
        assertThat("entries must not be equal!", environmentEntryA.equals(environmentEntryC), equalTo(false));
        assertThat("entries must not be equal!", environmentEntryB.equals(environmentEntryC), equalTo(false));
    }

    @Test
    public void testHashCode() {
        assertThat("hash codes must be equal!", environmentEntryA.hashCode(), equalTo(environmentEntryB.hashCode()));
        assertThat("hash codes must not be equal!", environmentEntryA.hashCode(), not(equalTo(environmentEntryC
                .hashCode())));
        assertThat("hash codes must not be equal!", environmentEntryB.hashCode(), not(equalTo(environmentEntryC
                .hashCode())));
    }
}