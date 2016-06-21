package hdm.pk070.jscheme.obj;

import hdm.pk070.jscheme.obj.function.builtin.SchemeBuiltinMinusTest;
import hdm.pk070.jscheme.obj.function.builtin.SchemeBuiltinPlusTest;
import hdm.pk070.jscheme.obj.simple.SchemeObjectTest;
import hdm.pk070.jscheme.obj.simple.SchemeConsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by patrick on 19.06.16.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeObjectTest.class, SchemeConsTest.class, SchemeBuiltinPlusTest.class,
        SchemeBuiltinMinusTest.class})
public class SchemeObjectTestSuite {
}
