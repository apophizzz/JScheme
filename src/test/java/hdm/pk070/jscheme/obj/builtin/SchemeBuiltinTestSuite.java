package hdm.pk070.jscheme.obj.builtin;

import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunctionTestSuite;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeBuiltinSimpleTypesTestSuite;
import hdm.pk070.jscheme.obj.builtin.syntax.SchemeBuiltinSyntaxTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeBuiltinSimpleTypesTestSuite.class, SchemeBuiltinFunctionTestSuite.class,
        SchemeBuiltinSyntaxTestSuite.class})
public class SchemeBuiltinTestSuite {
}
