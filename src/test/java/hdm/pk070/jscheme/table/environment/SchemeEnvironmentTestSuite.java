package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A test suite collecting all test classes concerning environments in JScheme.
 *
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({GlobalEnvironmentTest.class, LocalEnvironmentTest.class, EnvironmentEntryTest.class})
public class SchemeEnvironmentTestSuite {
}
