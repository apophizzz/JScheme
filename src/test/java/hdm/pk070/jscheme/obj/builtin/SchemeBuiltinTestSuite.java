package hdm.pk070.jscheme.obj.builtin;

import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinDivideTest;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinMinusTest;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinPlusTest;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinTimesTest;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeConsTest;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeObjectTest;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeFractionTest;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeIntegerTest;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloatTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeObjectTest.class, SchemeIntegerTest.class, SchemeFloatTest.class, SchemeConsTest.class,
        SchemeFractionTest.class, SchemeBuiltinPlusTest.class, SchemeBuiltinMinusTest.class,
        SchemeBuiltinTimesTest.class, SchemeBuiltinDivideTest.class})
public class SchemeBuiltinTestSuite {
}
