package hdm.pk070.jscheme.obj.builtin.simple;

import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeFractionTest;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeIntegerTest;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloatTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeObjectTest.class, SchemeIntegerTest.class, SchemeFractionTest.class, SchemeFloatTest
        .class, SchemeConsTest.class})
public class SchemeBuiltinSimpleTypesTestSuite {
}
