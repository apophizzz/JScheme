package hdm.pk070.jscheme.eval;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A test suite collecting all test classes dealing with evaluation of different JScheme objects.
 *
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({SchemeEvalPrimitivesTest.class, SymbolEvaluatorTest.class, ListEvaluatorTest.class})
public class SchemeEvalTestSuite {
}
