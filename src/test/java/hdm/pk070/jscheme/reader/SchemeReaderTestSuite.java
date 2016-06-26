package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.reader.obj.NumberObjReaderTest;
import hdm.pk070.jscheme.reader.obj.StringObjReaderTest;
import hdm.pk070.jscheme.reader.obj.SymbolObjReaderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by patrick on 19.06.16.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({NumberObjReaderTest.class, StringObjReaderTest.class, SymbolObjReaderTest.class,
        PushbackReaderLearningTest.class, SchemeCharacterReaderTest.class, SchemeReaderTest.class})
public class SchemeReaderTestSuite {
}
