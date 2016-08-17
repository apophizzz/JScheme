package hdm.pk070.jscheme.table;

import hdm.pk070.jscheme.table.hash.impl.StandardHashAlgProviderTest;
import hdm.pk070.jscheme.table.symbolTable.SchemeSymbolTableTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * A test suite collecting all the test classes which deal with the built-in symbol table as well as the hashing
 * algorithms in use.
 *
 * @author patrick.kleindienst
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({StandardHashAlgProviderTest.class, SchemeSymbolTableTest.class})
public class SchemeTableTestSuite {
}
