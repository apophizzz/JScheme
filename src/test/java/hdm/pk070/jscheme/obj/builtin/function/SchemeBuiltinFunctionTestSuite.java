package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.obj.builtin.function.base.SchemeBuiltinEqTest;
import hdm.pk070.jscheme.obj.builtin.function.list.SchemeBuiltinConsTest;
import hdm.pk070.jscheme.obj.builtin.function.list.SchemeBuiltinGetCarTest;
import hdm.pk070.jscheme.obj.builtin.function.list.SchemeBuiltinGetCdrTest;
import hdm.pk070.jscheme.obj.builtin.function.list.SchemeBuiltinIsConsTest;
import hdm.pk070.jscheme.obj.builtin.function.math.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeBuiltinPlusTest.class, SchemeBuiltinMinusTest.class, SchemeBuiltinTimesTest.class,
        SchemeBuiltinDivideTest.class, SchemeBuiltinAbsoluteTest.class, SchemeBuiltinConsTest.class,
        SchemeBuiltinGetCarTest.class, SchemeBuiltinGetCdrTest.class, SchemeBuiltinIsConsTest.class,
        SchemeBuiltinEqTest.class})
public class SchemeBuiltinFunctionTestSuite {
}
