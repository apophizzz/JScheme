package hdm.pk070.jscheme.symbolTable;

import hdm.pk070.jscheme.SchemeConstants;
import hdm.pk070.jscheme.hash.HashAlgProvider;
import hdm.pk070.jscheme.hash.impl.StandardHashAlgProvider;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;

import java.util.Objects;

/**
 *
 */
public class SchemeSymbolTable {

    private static int tableSize = SchemeConstants.INITIAL_SYMBOL_TABLE_SIZE;
    private static SchemeSymbolTable symbolTable;

    private HashAlgProvider hashAlgProvider;

    public static SchemeSymbolTable getInstance() {
        return withHashAlgorithm(new StandardHashAlgProvider());
    }

    public static SchemeSymbolTable withHashAlgorithm(HashAlgProvider hashAlgProvider) {
        if (Objects.isNull(symbolTable)) {
            symbolTable = new SchemeSymbolTable(new StandardHashAlgProvider());
        }
        return symbolTable;
    }

    private SchemeSymbolTable(HashAlgProvider hashAlgProvider) {
        this.hashAlgProvider = hashAlgProvider;
    }

    // TODO: Manage global symbols
    // If symbol table already contains object, return the corresponding SchemeSymbol object
    // If symbol table does not contain the object, construct SchemeSymbol object, store it and return it
    public SchemeSymbol getOrAdd(String symbolName) {
        Objects.requireNonNull(symbolName);

        // compute hash value from symbol name
        int hashVal = hashAlgProvider.computeHash(symbolName);

        // TODO: symbol table lookup
        return null;
    }


}
