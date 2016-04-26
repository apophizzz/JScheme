package hdm.pk070.jscheme.symbolTable;

import hdm.pk070.jscheme.obj.type.SchemeSymbol;

import java.util.Objects;

/**
 *
 */
public class SymbolTable {

    private static SymbolTable symbolTable;

    public static SymbolTable getInstance() {
        if (Objects.isNull(symbolTable)) {
            symbolTable = new SymbolTable();
        }
        return symbolTable;
    }

    private SymbolTable() {

    }

    public SchemeSymbol getOrAdd(String symbolName) {
        return null;
    }
}
