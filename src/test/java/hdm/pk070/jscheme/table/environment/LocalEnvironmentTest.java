package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeInteger;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * A test class for {@link LocalEnvironment}
 *
 * @author patrick.kleindienst
 */
public class LocalEnvironmentTest {

    private LocalEnvironment localEnvironment;
    private SchemeSymbol localEnvKey1;
    private SchemeSymbol localEnvKey2;

    @Before
    public void setUp() throws Exception {
        localEnvKey1 = new SchemeSymbol("foo");
        localEnvKey2 = new SchemeSymbol("bar");

        LocalEnvironment parentEnvironment = LocalEnvironment.withSize(10);
        parentEnvironment.add(EnvironmentEntry.create(localEnvKey1, new SchemeInteger(42)));

        localEnvironment = LocalEnvironment.withSizeAndParent(10, parentEnvironment);
        localEnvironment.add(EnvironmentEntry.create(localEnvKey2, new SchemeString("foobar")));
    }

    @Test
    public void testGetFromLocalEnvironment() {
        Optional<EnvironmentEntry> environmentEntry = localEnvironment.get(localEnvKey2);

        assertThat(environmentEntry, notNullValue());
        assertThat(environmentEntry.isPresent(), equalTo(true));
        assertThat(environmentEntry.orElse(getDummyStringEnvironmentEntry()).getValue(), equalTo(new SchemeString
                ("foobar")));
    }

    @Test
    public void testGetFromParentEnvironment() {
        Optional<EnvironmentEntry> environmentEntry = localEnvironment.get(localEnvKey1);

        assertThat(environmentEntry, notNullValue());
        assertThat(environmentEntry.isPresent(), equalTo(true));
        assertThat(environmentEntry.orElse(getDummyStringEnvironmentEntry()).getValue(), equalTo(new SchemeInteger
                (42)));
    }

    @Test
    public void testGetNonExistingEntryFromEnvironment() {
        Optional<EnvironmentEntry> nonExistingEntry = localEnvironment.get(new SchemeSymbol("non-existing-key"));

        assertThat(nonExistingEntry, notNullValue());
        assertThat(nonExistingEntry.isPresent(), equalTo(false));
    }

    private EnvironmentEntry getDummyStringEnvironmentEntry() {
        return EnvironmentEntry.create(new SchemeSymbol(""), new SchemeSymbol(""));
    }
}