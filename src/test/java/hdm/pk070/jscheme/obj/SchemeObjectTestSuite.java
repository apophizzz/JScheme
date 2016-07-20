package hdm.pk070.jscheme.obj;

import hdm.pk070.jscheme.obj.builtin.SchemeBuiltinTestSuite;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunctionTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by patrick on 19.06.16.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeBuiltinTestSuite.class, SchemeCustomUserFunctionTest.class})
public class SchemeObjectTestSuite {
}
