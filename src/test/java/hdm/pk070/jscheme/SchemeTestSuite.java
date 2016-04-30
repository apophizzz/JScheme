package hdm.pk070.jscheme;

import hdm.pk070.jscheme.hash.impl.StandardHashAlgProviderTest;
import hdm.pk070.jscheme.reader.PushbackReaderLearningTest;
import hdm.pk070.jscheme.reader.SchemeCharacterReaderTest;
import hdm.pk070.jscheme.reader.SchemeReaderTest;
import hdm.pk070.jscheme.reader.obj.StringObjReaderTest;
import hdm.pk070.jscheme.symbolTable.SchemeSymbolTableTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeReaderTest.class, StringObjReaderTest.class, SchemeCharacterReaderTest.class,
        PushbackReaderLearningTest.class,
        StandardHashAlgProviderTest.class, SchemeSymbolTableTest.class})
public class SchemeTestSuite {
}
