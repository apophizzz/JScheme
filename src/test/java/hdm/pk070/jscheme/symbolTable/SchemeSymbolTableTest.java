package hdm.pk070.jscheme.symbolTable;

import hdm.pk070.jscheme.SchemeConstants;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import hdm.pk070.jscheme.util.ReflectionMethodParam;
import hdm.pk070.jscheme.util.ReflectionUtils;
import hdm.pk070.jscheme.util.exception.ReflectionMethodCallException;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class SchemeSymbolTableTest {


    private SchemeSymbolTable schemeSymbolTable;
    private SchemeSymbol[] symbolArray;


    @Before
    public void setUp() {
        schemeSymbolTable = SchemeSymbolTable.getInstance();
        symbolArray = (SchemeSymbol[]) ReflectionUtils.getAttributeVal(schemeSymbolTable, "symbolTable");
    }

    @Test
    public void testGetOrAdd() throws Exception {
        SchemeSymbol testSymbol = schemeSymbolTable.getOrAdd("foobar");
        SchemeSymbol validationSymbol = schemeSymbolTable.getOrAdd("foobar");

        assertThat("Symbol references must point to same object!", (testSymbol == validationSymbol), equalTo(true));
    }

    @Test
    public void testAddToTable() {
        int randomIndex = getRandomTableIndex();
        SchemeSymbol testSymbol = new SchemeSymbol("foobar");

        ReflectionUtils.invokeMethod(schemeSymbolTable, "addToTable", new ReflectionMethodParam(SchemeSymbol.class,
                testSymbol), new ReflectionMethodParam(int.class, randomIndex));

        assertThat("Slot should not be empty!", symbolArray[randomIndex], notNullValue());
        assertThat("Slot value does not match expected symbol!", symbolArray[randomIndex], equalTo(testSymbol));
    }


    @Test
    public void testIsFreeSlot() {
        int randomIndex = getRandomTableIndex();

        assertThat(ReflectionUtils.invokeMethod(schemeSymbolTable, "isFreeSlot", new ReflectionMethodParam(int.class,
                randomIndex)), equalTo(true));
        symbolArray[randomIndex] = new SchemeSymbol("foobar");
        assertThat(ReflectionUtils.invokeMethod(schemeSymbolTable, "isFreeSlot", new ReflectionMethodParam(int.class,
                randomIndex)), equalTo(false));

    }

    @Test(expected = ReflectionMethodCallException.class)
    public void testIsFreeSlotThrowsException() {
        ReflectionUtils.invokeMethod(schemeSymbolTable, "isFreeSlot", new ReflectionMethodParam(int.class, Integer
                .MAX_VALUE));
    }

    @Test
    public void testIncrementFillSize() {
        int tableFillSizeBefore = (int) ReflectionUtils.getAttributeVal(schemeSymbolTable, "tableFillSize");
        ReflectionUtils.invokeMethod(schemeSymbolTable, "incrementFillSize");
        int tableFillSizeAfter = (int) ReflectionUtils.getAttributeVal(schemeSymbolTable, "tableFillSize");

        assertThat("Table fill size is not incremented!", tableFillSizeAfter > tableFillSizeBefore, equalTo(true));
    }

    @Test
    public void testGetNextPowerOfTwoMinusOne() {
        int initialTableSize = (int) ReflectionUtils.getAttributeVal(schemeSymbolTable, "tableSize");
        int nextTableSize = (int) ReflectionUtils.invokeMethod(schemeSymbolTable, "getNextPowerOfTwoMinusOne");

        assertThat("Subsequent table size must be greater than previous!", nextTableSize > initialTableSize, equalTo
                (true));
        assertThat(nextTableSize == ((initialTableSize + 1) * 2 - 1), equalTo(true));
    }

    @Test
    public void testStartRehash() {
        int symbolTableSizeBeforeRehash = (int) ReflectionUtils.getAttributeVal(schemeSymbolTable, "tableSize");
        SchemeSymbol[] symbolTableBeforeRehash = (SchemeSymbol[]) ReflectionUtils.getAttributeVal(schemeSymbolTable,
                "symbolTable");

        ReflectionUtils.invokeMethod(schemeSymbolTable, "startRehash");

        int symbolTableSizeAfterRehash = (int) ReflectionUtils.getAttributeVal(schemeSymbolTable, "tableSize");
        SchemeSymbol[] symbolTableAfterRehash = (SchemeSymbol[]) ReflectionUtils.getAttributeVal(schemeSymbolTable,
                "symbolTable");

        assertThat("New symbol table size does not match expected value!", symbolTableSizeAfterRehash == (
                (symbolTableSizeBeforeRehash + 1) * 2 - 1), equalTo(true));
        assertThat("Length of new symbol table does not match expected value!", symbolTableAfterRehash.length == (
                (symbolTableBeforeRehash.length + 1) * 2 - 1), equalTo(true));
    }

    private int getRandomTableIndex() {
        return new Random().nextInt((SchemeConstants.INITIAL_SYMBOL_TABLE_SIZE - 1));
    }
}
