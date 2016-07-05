package hdm.pk070.jscheme;

import hdm.pk070.jscheme.eval.SchemeEvalTestSuite;
import hdm.pk070.jscheme.obj.SchemeObjectTestSuite;
import hdm.pk070.jscheme.reader.SchemeReaderTestSuite;
import hdm.pk070.jscheme.table.SchemeTableTestSuite;
import hdm.pk070.jscheme.table.environment.SchemeEnvironmentTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A test suite for collecting all the test classes of the JScheme project in a single test suite.
 *
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeObjectTestSuite.class, SchemeReaderTestSuite.class, SchemeEvalTestSuite.class,
        SchemeEnvironmentTestSuite.class, SchemeTableTestSuite.class})
public class JSchemeTestSuite {
}
