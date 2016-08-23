package hdm.pk070.jscheme.table.symbolTable;

import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.ResizableTable;
import hdm.pk070.jscheme.table.hash.HashAlgProvider;
import hdm.pk070.jscheme.table.hash.impl.StandardHashAlgProvider;

import java.util.Objects;

/**
 * As the name indicated, SchemeSymbolTable is a symbol store. During JScheme execution, every symbol is only saved
 * once. Saving symbols within the table is done by computing a hash out of the value which shall be added, whereas
 * searching a value is done by hashing the key. The exact hashing algorithm can be replaced but has to implement
 * {@link HashAlgProvider}.
 *
 * @author patrick.kleindienst
 */
public class SchemeSymbolTable extends ResizableTable<String, SchemeSymbol> {

    private static SchemeSymbolTable schemeSymbolTableInstance = null;

    private final HashAlgProvider hashAlgProvider;

    /**
     * Instantiate a symbol table with a default hashing algorithm. Consider that the symbol table is implemented as
     * a singleton.
     *
     * @return The symbol table instance.
     */
    public static SchemeSymbolTable getInstance() {
        return withHashAlgorithm(new StandardHashAlgProvider());
    }

    /**
     * Instantiate a symbol table with a certain hashing algorithm. Consider that the symbol table is implemented as
     * a singleton.
     *
     * @param hashAlgProvider
     *         Hashing algorithm which implements {@link HashAlgProvider}.
     * @return The symbol table instance.
     */
    public static SchemeSymbolTable withHashAlgorithm(final HashAlgProvider hashAlgProvider) {
        if (Objects.isNull(schemeSymbolTableInstance)) {
            schemeSymbolTableInstance = new SchemeSymbolTable(hashAlgProvider);
        }
        return schemeSymbolTableInstance;
    }

    private SchemeSymbolTable(final HashAlgProvider hashAlgProvider) {
        this.hashAlgProvider = hashAlgProvider;
    }

    @Override
    protected SchemeSymbol handleDuplicateEntries(final SchemeSymbol newEntry, final SchemeSymbol oldEntry, int
            oldEntryIndex) {
        return oldEntry;
    }

    @Override
    protected int keyToHashVal(String key) {
        return hashAlgProvider.computeHash(key);
    }

    @Override
    protected int valueToHashVal(SchemeSymbol value) {
        return keyToHashVal(value.getValue());
    }

    @Override
    protected boolean keysMatch(String searchedKey, SchemeSymbol entryFound) {
        return searchedKey.equals(entryFound.getValue());
    }

    @Override
    protected boolean entriesMatch(SchemeSymbol entryToAdd, SchemeSymbol existingEntry) {
        return entryToAdd.equals(existingEntry);
    }
}
