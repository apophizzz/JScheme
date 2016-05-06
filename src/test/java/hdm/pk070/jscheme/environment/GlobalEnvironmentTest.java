package hdm.pk070.jscheme.environment;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeInteger;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import hdm.pk070.jscheme.util.ReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by patrick on 05.05.16.
 */
public class GlobalEnvironmentTest {

    private GlobalEnvironment globalEnvironment;

    @Before
    public void setUp() {
        globalEnvironment = GlobalEnvironment.getInstance();
    }

    @Test
    public void testGetEntryFromEnvironment() {
        EnvironmentEntry[] environmentEntries = (EnvironmentEntry[]) ReflectionUtils.getAttributeVal
                (globalEnvironment, "environmentEntries");
        SchemeSymbol keySymbol = new SchemeSymbol("foo");
        environmentEntries[0] = EnvironmentEntry.create(keySymbol, new SchemeInteger(42));
        SchemeObject result = globalEnvironment.get(keySymbol);

        assertThat("result must not be null!", result, notNullValue());
        assertThat("result must be of type SchemeInteger!", result.typeOf(SchemeInteger.class), equalTo(true));
        assertThat("result does not match expected value!", result, equalTo(new SchemeInteger(42)));
    }

    @Test
    public void testGetReturnsNullOnMissingEntry() {
        SchemeObject result = globalEnvironment.get(new SchemeSymbol("bar"));

        assertThat(result, nullValue());
    }

    @Test
    public void testPutEntryIntoEnvironment() {
        globalEnvironment = GlobalEnvironment.getInstance();
        globalEnvironment.put(new SchemeSymbol("foo"), new SchemeInteger(42));
        EnvironmentEntry[] environmentEntries = (EnvironmentEntry[]) ReflectionUtils.getAttributeVal
                (globalEnvironment, "environmentEntries");

        assertThat(environmentEntries.length, equalTo(1));
    }

    @After
    public void tearDown() {
        // reset environment for each test since it is a singleton
        EnvironmentEntry[] environmentEntries = (EnvironmentEntry[]) ReflectionUtils.getAttributeVal
                (globalEnvironment, "environmentEntries");
        environmentEntries = new EnvironmentEntry[511];
    }
}