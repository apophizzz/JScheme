package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeInteger;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import hdm.pk070.jscheme.util.ReflectionCallArg;
import hdm.pk070.jscheme.util.ReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * A test class for {@link GlobalEnvironment}
 *
 * @author patrick.kleindienst
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
        globalEnvironment.add(EnvironmentEntry.create(testSymbol, new SchemeInteger(42)));

        assertThat(ReflectionUtils.getAttributeVal(globalEnvironment, "currentFillSize"), equalTo(1));
        assertThat(globalEnvironmentContains(EnvironmentEntry.create(testSymbol, new SchemeInteger(42))),
                equalTo(true));
    }

    @Test
    public void testGetEntryFromEnvironment() throws SchemeError {
        SchemeSymbol keySymbol = new SchemeSymbol("foo");
        globalEnvironment.add(EnvironmentEntry.create(keySymbol, new SchemeInteger(42)));
        Optional<EnvironmentEntry> resultOptional = globalEnvironment.get(keySymbol);

        assertThat("result must not be null!", resultOptional, notNullValue());
        assertThat("result must not be missing!", resultOptional.isPresent(), equalTo(true));
        assertThat("result must be of type SchemeInteger!", resultOptional.orElse(getDummyIntegerEnvironmentEntry())
                .getValue().typeOf(SchemeInteger
                        .class), equalTo(true));
        assertThat("result does not match expected value!", resultOptional.orElse(getDummyIntegerEnvironmentEntry())
                .getValue(), equalTo(new
                SchemeInteger(42)));
    }

    @Test
    public void testGetReturnsEmptyOptionalOnMissingEntry() {
        Optional<EnvironmentEntry> resultOptional = globalEnvironment.get(new SchemeSymbol("bar"));

        assertThat(resultOptional, notNullValue());
        assertThat(resultOptional.isPresent(), equalTo(false));
    }

    @Test
    public void testRehash() {
        ReflectionUtils.invokeMethod(globalEnvironment, "doRehash");
        Object[] environmentEntries = (Object[]) ReflectionUtils.getAttributeVal
                (globalEnvironment, "entries");

        assertThat(environmentEntries.length, equalTo((511 + 1) * 2 - 1));
    }

    @Test
    public void testKeysMatch() {
        SchemeSymbol keySymbol = new SchemeSymbol("foo");
        EnvironmentEntry environmentEntry = EnvironmentEntry.create(keySymbol, new SchemeInteger(42));
        Boolean keysMatch = (Boolean) ReflectionUtils.invokeMethod(globalEnvironment, "keysMatch", new
                ReflectionCallArg(SchemeSymbol.class, keySymbol), new ReflectionCallArg(EnvironmentEntry
                .class, environmentEntry));

        assertThat("boolean result must not be null!", keysMatch, notNullValue());
        assertThat("boolean result must be true!", keysMatch, equalTo(true));
    }

    @Test
    public void testDifferentKeysDoNotMatch() {
        SchemeSymbol keySymbol = new SchemeSymbol("bar");
        EnvironmentEntry environmentEntry = EnvironmentEntry.create(new SchemeSymbol("foo"), new SchemeInteger(42));
        Boolean keysMatch = (Boolean) ReflectionUtils.invokeMethod(globalEnvironment, "keysMatch", new
                ReflectionCallArg(SchemeSymbol.class, keySymbol), new ReflectionCallArg(EnvironmentEntry
                .class, environmentEntry));

        assertThat("boolean result must not be null!", keysMatch, notNullValue());
        assertThat("boolean result must be false!", keysMatch, equalTo(false));
    }

    @Test
    public void testEntriesMatch() {
        SchemeSymbol key1 = new SchemeSymbol("foo");
        EnvironmentEntry envEntry1 = EnvironmentEntry.create(key1, new SchemeInteger(42));
        EnvironmentEntry envEntry2 = EnvironmentEntry.create(key1, new SchemeInteger(99));
        Boolean entriesMatch = (Boolean) ReflectionUtils.invokeMethod(globalEnvironment, "entriesMatch", new
                ReflectionCallArg(EnvironmentEntry
                .class, envEntry1), new ReflectionCallArg(EnvironmentEntry.class, envEntry2));

        assertThat("boolean result must not be null!", entriesMatch, notNullValue());
        assertThat("boolean result must be true!", entriesMatch, equalTo(true));
    }

    @After
    public void tearDown() {
        // reset environment for each test since it is a singleton
        ReflectionUtils.setAttributeVal(globalEnvironment, "entries", new Object[511]);
    }

    private boolean globalEnvironmentContains(EnvironmentEntry expectedEntry) {
        Object[] currentGlobalEnvironmentEntries = (Object[]) ReflectionUtils.getAttributeVal
                (globalEnvironment, "entries");
        Optional<Object> entryOptional = Arrays.asList(currentGlobalEnvironmentEntries).stream()
                .filter(entry -> Objects.nonNull(entry))
                .filter(envEntry -> envEntry.equals(expectedEntry))
                .findAny();

        return entryOptional.isPresent();
    }

    private EnvironmentEntry getDummyIntegerEnvironmentEntry() {
        return EnvironmentEntry.create(new SchemeSymbol(""), new SchemeInteger(-1));
    }
}