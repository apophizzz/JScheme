package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by patrick on 19.06.16.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({GlobalEnvironmentTest.class, LocalEnvironmentTest.class, EnvironmentEntryTest.class})
public class SchemeEnvironmentTestSuite {
}
