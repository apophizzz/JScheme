package hdm.pk070.jscheme.obj.builtin;

import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinMinusTest;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinPlusTest;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinTimesTest;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeConsTest;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeObjectTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeObjectTest.class, SchemeConsTest.class, SchemeBuiltinPlusTest.class,
        SchemeBuiltinMinusTest.class, SchemeBuiltinTimesTest.class})
public class SchemeBuiltinTestSuite {
}
