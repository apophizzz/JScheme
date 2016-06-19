package hdm.pk070.jscheme.table;

import hdm.pk070.jscheme.table.hash.impl.StandardHashAlgProviderTest;
import hdm.pk070.jscheme.table.symbolTable.SchemeSymbolTableTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by patrick on 19.06.16.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({StandardHashAlgProviderTest.class, SchemeSymbolTableTest.class})
public class SchemeTableTestSuite {
}
