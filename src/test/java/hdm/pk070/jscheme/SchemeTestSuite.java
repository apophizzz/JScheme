package hdm.pk070.jscheme;

import hdm.pk070.jscheme.table.environment.GlobalEnvironmentTest;
import hdm.pk070.jscheme.eval.SchemeEvalTest;
import hdm.pk070.jscheme.eval.SymbolEvaluatorTest;
import hdm.pk070.jscheme.table.hash.impl.StandardHashAlgProviderTest;
import hdm.pk070.jscheme.obj.SchemeObjectTest;
import hdm.pk070.jscheme.obj.type.SchemeConsTest;
import hdm.pk070.jscheme.reader.PushbackReaderLearningTest;
import hdm.pk070.jscheme.reader.SchemeCharacterReaderTest;
import hdm.pk070.jscheme.reader.SchemeReaderTest;
import hdm.pk070.jscheme.reader.obj.IntegerObjReaderTest;
import hdm.pk070.jscheme.reader.obj.StringObjReaderTest;
import hdm.pk070.jscheme.reader.obj.SymbolObjReaderTest;
import hdm.pk070.jscheme.table.symbolTable.SchemeSymbolTableTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A test suite for collecting all the single test classes of the JScheme project.
 *
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeObjectTest.class, SchemeConsTest.class, SchemeReaderTest.class, StringObjReaderTest.class,
        IntegerObjReaderTest.class,
        SymbolObjReaderTest.class,
        SchemeCharacterReaderTest.class,
        PushbackReaderLearningTest.class,
        StandardHashAlgProviderTest.class, SchemeSymbolTableTest.class, GlobalEnvironmentTest.class,
        SchemeEvalTest.class, SymbolEvaluatorTest.class})
public class SchemeTestSuite {
}
