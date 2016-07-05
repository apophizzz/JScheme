package hdm.pk070.jscheme.obj.builtin.function;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeBuiltinPlusTest.class, SchemeBuiltinMinusTest.class, SchemeBuiltinTimesTest.class,
        SchemeBuiltinDivideTest.class, SchemeBuiltinAbsoluteTest.class, SchemeBuiltinConsTest.class,
        SchemeBuiltinGetCarTest.class, SchemeBuiltinGetCdrTest.class, SchemeBuiltinIsConsTest.class})
public class SchemeBuiltinFunctionTestSuite {
}
