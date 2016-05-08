package hdm.pk070.jscheme.symbolTable;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.hash.HashAlgProvider;
import hdm.pk070.jscheme.hash.impl.StandardHashAlgProvider;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import hdm.pk070.jscheme.util.ReflectionMethodParam;
import hdm.pk070.jscheme.util.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class SchemeSymbolTableTest {


    private SchemeSymbolTable schemeSymbolTable;
    private Object[] entries;


    @Before
    public void setUp() throws Exception {
        Constructor<SchemeSymbolTable> constructor = SchemeSymbolTable.class.getDeclaredConstructor(HashAlgProvider
                .class);
        constructor.setAccessible(true);
        schemeSymbolTable = constructor.newInstance(new StandardHashAlgProvider());
        ReflectionUtils.setAttributeVal(schemeSymbolTable, "entries", new Object[511]);
        entries = (Object[]) ReflectionUtils.getAttributeVal(schemeSymbolTable, "entries");
    }


    @Test
    public void testAdd() throws SchemeError {
        SchemeSymbol testSymbol = new SchemeSymbol("foobar");
        SchemeSymbol schemeSymbol = schemeSymbolTable.add(testSymbol);
        int currentFillSize = (int) ReflectionUtils.getAttributeVal(schemeSymbolTable, "currentFillSize");

        assertThat("schemeSymbol must not be null!", schemeSymbol, notNullValue());
        assertThat("schemeSymbol must be equal to testSymbol!", schemeSymbol, equalTo(testSymbol));
        assertThat("fill size of symbol table must be equal to 1!", currentFillSize, equalTo(1));
    }

    @Test(expected = NullPointerException.class)
    public void testAddNullThrowsException() throws SchemeError {
        schemeSymbolTable.add(null);
    }

    @Test
    public void testGet() throws SchemeError {
        SchemeSymbol schemeSymbol = schemeSymbolTable.add(new SchemeSymbol("foobar"));
        Optional<SchemeSymbol> searchedSymbol = schemeSymbolTable.get(schemeSymbol.getValue());

        assertThat(searchedSymbol, notNullValue());
        assertThat(searchedSymbol.isPresent(), equalTo(true));
        assertThat(searchedSymbol.get() == schemeSymbol, equalTo(true));
    }

    @Test(expected = NullPointerException.class)
    public void testGetNullThrowsException() {
        schemeSymbolTable.get(null);
    }

    @Test
    public void testGetMissingEntryReturnsEmptyOptional() {
        Optional<SchemeSymbol> symbolOptional = schemeSymbolTable.get("missing");

        assertThat(symbolOptional, notNullValue());
        assertThat(symbolOptional.isPresent(), equalTo(false));
    }

    @Test
    public void testDoRehash() {
        Object[] entriesBeforeRehash = entries;
        ReflectionUtils.invokeMethod(schemeSymbolTable, "doRehash");
        Object[] entriesAfterRehash = (Object[]) ReflectionUtils.getAttributeVal(schemeSymbolTable, "entries");

        assertThat("entries must not be null after rehash!", entriesAfterRehash, notNullValue());
        assertThat("entries length must be greater after rehash!", entriesAfterRehash.length > entriesBeforeRehash
                .length, equalTo(true));
        assertThat("entries length does not match expected value!", ((entriesBeforeRehash.length + 1) * 2 - 1),
                equalTo(entriesAfterRehash.length));

    }


    @Test
    public void testEntryExistsAt() {
        entries[50] = new SchemeSymbol("foobar");
        Object entries = ReflectionUtils.getAttributeVal(schemeSymbolTable, "entries");
        boolean entryExistsAt = (boolean) ReflectionUtils.invokeMethod(schemeSymbolTable, "entryExistsAt", new
                ReflectionMethodParam(int.class, 50));
        boolean entryNotExistsAt = (boolean) ReflectionUtils.invokeMethod(schemeSymbolTable, "entryExistsAt", new
                ReflectionMethodParam(int.class, 51));

        assertThat("entry must exist!", entryExistsAt, equalTo(true));
        assertThat("entry must not exist", !entryNotExistsAt, equalTo(true));
    }

    @Test
    public void testIncrementFillSize() {
        int tableFillSizeBefore = (int) ReflectionUtils.getAttributeVal(schemeSymbolTable, "currentFillSize");
        ReflectionUtils.invokeMethod(schemeSymbolTable, "incrementFillSize");
        int tableFillSizeAfter = (int) ReflectionUtils.getAttributeVal(schemeSymbolTable, "currentFillSize");

        assertThat("Table fill size is not incremented!", tableFillSizeAfter > tableFillSizeBefore, equalTo(true));
    }

    @Test
    public void testGetNextPowerOfTwoMinusOne() {
        int initialTableSize = (int) ReflectionUtils.getAttributeVal(schemeSymbolTable, "currentTableSize");
        int nextTableSize = (int) ReflectionUtils.invokeMethod(schemeSymbolTable, "nextPowerOfTwoMinusOne");

        assertThat("Subsequent table size must be greater than previous!", nextTableSize > initialTableSize, equalTo
                (true));
        assertThat(nextTableSize == ((initialTableSize + 1) * 2 - 1), equalTo(true));
    }

    @Test
    public void testKeysMatch() {
        boolean isMatchingKeys = (boolean) ReflectionUtils.invokeMethod(schemeSymbolTable, "keysMatch", new
                ReflectionMethodParam(String.class,
                "foobar"), new ReflectionMethodParam(SchemeSymbol.class, new SchemeSymbol("foobar")));
        boolean isNotMatchingKeys = (boolean) ReflectionUtils.invokeMethod(schemeSymbolTable, "keysMatch", new
                ReflectionMethodParam(String.class,
                "foobar"), new ReflectionMethodParam(SchemeSymbol.class, new SchemeSymbol("barfoo")));

        assertThat("keys must match!", isMatchingKeys, equalTo(true));
        assertThat("keys must not match!", !isNotMatchingKeys, equalTo(true));
    }

    @Test
    public void testIsSameEntry() {
        boolean isSameEntry = (boolean) ReflectionUtils.invokeMethod(schemeSymbolTable, "isSameEntry", new
                ReflectionMethodParam
                (SchemeSymbol.class,
                        new SchemeSymbol("foobar")), new ReflectionMethodParam(SchemeSymbol.class, new SchemeSymbol
                ("foobar")));
        boolean isSameEntry1 = (boolean) ReflectionUtils.invokeMethod(schemeSymbolTable, "isSameEntry", new
                ReflectionMethodParam
                (SchemeSymbol.class,
                        new SchemeSymbol("foobar")), new ReflectionMethodParam(SchemeSymbol.class, new SchemeSymbol
                ("barfoo")));

        assertThat("entries must be equal!", isSameEntry, equalTo(true));
        assertThat("entries must not be equal!", isSameEntry1, equalTo(false));
    }


}
