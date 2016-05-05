package hdm.pk070.jscheme;

import hdm.pk070.jscheme.eval.SchemeEvalTest;
import hdm.pk070.jscheme.eval.SymbolEvaluatorTest;
import hdm.pk070.jscheme.hash.impl.StandardHashAlgProviderTest;
import hdm.pk070.jscheme.reader.PushbackReaderLearningTest;
import hdm.pk070.jscheme.reader.SchemeCharacterReaderTest;
import hdm.pk070.jscheme.reader.SchemeReaderTest;
import hdm.pk070.jscheme.reader.obj.IntegerObjReaderTest;
import hdm.pk070.jscheme.reader.obj.StringObjReaderTest;
import hdm.pk070.jscheme.reader.obj.SymbolObjReader;
import hdm.pk070.jscheme.reader.obj.SymbolObjReaderTest;
import hdm.pk070.jscheme.symbolTable.SchemeSymbolTableTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A test suite for collecting all the single test classes of the JScheme project.
 *
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeReaderTest.class, StringObjReaderTest.class, IntegerObjReaderTest.class,
        SymbolObjReaderTest.class,
        SchemeCharacterReaderTest.class,
        PushbackReaderLearningTest.class,
        StandardHashAlgProviderTest.class, SchemeSymbolTableTest.class, SchemeEvalTest.class, SymbolEvaluatorTest
        .class})
public class SchemeTestSuite {
}
