package hdm.pk070.jscheme;

import hdm.pk070.jscheme.reader.PushbackReaderLearningTest;
import hdm.pk070.jscheme.reader.SchemeCharacterReaderTest;
import hdm.pk070.jscheme.reader.SchemeReaderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeReaderTest.class, SchemeCharacterReaderTest.class, PushbackReaderLearningTest.class})
public class SchemeTestSuite {
}
