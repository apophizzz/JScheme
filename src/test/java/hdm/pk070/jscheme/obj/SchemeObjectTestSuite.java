package hdm.pk070.jscheme.obj;

import hdm.pk070.jscheme.obj.builtin.SchemeBuiltinTestSuite;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunctionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A test suite merging the built-in and custom JScheme object test suites.
 *
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeBuiltinTestSuite.class, SchemeCustomUserFunctionTest.class})
public class SchemeObjectTestSuite {
}
