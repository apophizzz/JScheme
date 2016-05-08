package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeInteger;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import hdm.pk070.jscheme.util.ReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

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
    public void testPutEntryIntoEnvironment() throws SchemeError {
        SchemeSymbol testSymbol = new SchemeSymbol("foo");
        globalEnvironment.put(testSymbol, new SchemeInteger(42));

        assertThat(ReflectionUtils.getAttributeVal(globalEnvironment, "currentFillSize"), equalTo(1));
        assertThat(globalEnvironmentContains(EnvironmentEntry.create(testSymbol, new SchemeInteger(42))),
                equalTo(true));
    }

    @Test
    public void testGetEntryFromEnvironment() throws SchemeError {
        SchemeSymbol keySymbol = new SchemeSymbol("foo");
        globalEnvironment.put(keySymbol, new SchemeInteger(42));
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
    public void testRehash() {
        ReflectionUtils.invokeMethod(globalEnvironment, "startRehash");
        EnvironmentEntry[] environmentEntries = (EnvironmentEntry[]) ReflectionUtils.getAttributeVal
                (globalEnvironment, "currentGlobalEnvironmentEntries");

        assertThat(environmentEntries.length, equalTo((511 + 1) * 2 - 1));
    }

    @After
    public void tearDown() {
        // reset environment for each test since it is a singleton
        EnvironmentEntry[] environmentEntries = (EnvironmentEntry[]) ReflectionUtils.getAttributeVal
                (globalEnvironment, "currentGlobalEnvironmentEntries");
        environmentEntries = new EnvironmentEntry[511];
    }

    private boolean globalEnvironmentContains(EnvironmentEntry expectedEntry) {
        EnvironmentEntry[] currentGlobalEnvironmentEntries = (EnvironmentEntry[]) ReflectionUtils.getAttributeVal
                (globalEnvironment,
                        "currentGlobalEnvironmentEntries");
        Optional<EnvironmentEntry> entryOptional = Arrays.asList(currentGlobalEnvironmentEntries).stream()
                .filter(entry -> Objects.nonNull(entry))
                .filter(envEntry -> envEntry.equals(expectedEntry))
                .findAny();

        return entryOptional.isPresent();
    }
}