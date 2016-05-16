package hdm.pk070.jscheme.table.symbolTable;

import hdm.pk070.jscheme.table.hash.HashAlgProvider;
import hdm.pk070.jscheme.table.hash.impl.StandardHashAlgProvider;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import hdm.pk070.jscheme.table.ResizableTable;

import java.util.Objects;

/**
 *
 */
public class SchemeSymbolTable extends ResizableTable<String, SchemeSymbol> {

    private static SchemeSymbolTable schemeSymbolTableInstance = null;

    private HashAlgProvider hashAlgProvider;

    public static SchemeSymbolTable getInstance() {
        return withHashAlgorithm(new StandardHashAlgProvider());
    }

    public static SchemeSymbolTable withHashAlgorithm(HashAlgProvider hashAlgProvider) {
        if (Objects.isNull(schemeSymbolTableInstance)) {
            schemeSymbolTableInstance = new SchemeSymbolTable(hashAlgProvider);
        }
        return schemeSymbolTableInstance;
    }

    private SchemeSymbolTable(HashAlgProvider hashAlgProvider) {
        this.hashAlgProvider = hashAlgProvider;
    }

    @Override
    protected SchemeSymbol handleDuplicateEntries(SchemeSymbol newEntry, SchemeSymbol oldEntry, int oldEntryIndex) {
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
        if (entryToAdd.equals(existingEntry)) {
            return true;
        }
        return false;
    }
}
