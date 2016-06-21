package hdm.pk070.jscheme.obj;

import hdm.pk070.jscheme.obj.builtin.SchemeBuiltinTestSuite;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinMinusTest;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinPlusTest;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeObjectTest;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeConsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by patrick on 19.06.16.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeBuiltinTestSuite.class})
public class SchemeObjectTestSuite {
}
