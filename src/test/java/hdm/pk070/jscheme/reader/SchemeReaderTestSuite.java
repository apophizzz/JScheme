package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.reader.obj.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite summarizing all test classes concerning read functionality.
 *
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({NumberObjReaderTest.class, StringObjReaderTest.class, SymbolObjReaderTest.class,
        PushbackReaderLearningTest.class, SchemeCharacterReaderTest.class, SchemeReaderTest.class,
        QuotedInputReaderTest.class})
public class SchemeReaderTestSuite {
}
