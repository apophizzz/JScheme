package hdm.pk070.jscheme.obj.builtin.syntax;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeBuiltinDefineTest.class, SchemeBuiltinIfTest.class, SchemeBuiltinLambdaTest.class,
        SchemeBuiltinQuoteTest.class})
public class SchemeBuiltinSyntaxTestSuite {
}
