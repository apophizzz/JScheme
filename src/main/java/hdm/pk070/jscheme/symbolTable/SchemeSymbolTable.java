package hdm.pk070.jscheme.symbolTable;

import hdm.pk070.jscheme.SchemeConstants;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.hash.HashAlgProvider;
import hdm.pk070.jscheme.hash.impl.StandardHashAlgProvider;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;

import java.util.Objects;

/**
 *
 */
public class SchemeSymbolTable {

    private static int tableSize = SchemeConstants.INITIAL_SYMBOL_TABLE_SIZE;
    private static SchemeSymbolTable schemeSymbolTable;

    private SchemeSymbol[] symbolTable;
    private int tableFillSize;
    private HashAlgProvider hashAlgProvider;

    public static SchemeSymbolTable getInstance() {
        return withHashAlgorithm(new StandardHashAlgProvider());
    }

    public static SchemeSymbolTable withHashAlgorithm(HashAlgProvider hashAlgProvider) {
        if (Objects.isNull(schemeSymbolTable)) {
            schemeSymbolTable = new SchemeSymbolTable(hashAlgProvider);
        }
        return schemeSymbolTable;
    }

    private SchemeSymbolTable(HashAlgProvider hashAlgProvider) {
        this.hashAlgProvider = hashAlgProvider;
        this.symbolTable = new SchemeSymbol[tableSize];
        this.tableFillSize = 0;
    }


    public SchemeSymbol getOrAdd(String symbolName) {
        Objects.requireNonNull(symbolName);

        int nextIndex;
        int hashVal = hashAlgProvider.computeHash(symbolName);
        int startIndex = hashVal % tableSize;
        SchemeSymbol symbol;
        nextIndex = startIndex;

        for (; ; ) {

            // In case the current index marks a free slot
            if (isFreeSlot(nextIndex)) {
                SchemeSymbol schemeSymbol = SchemeSymbol.createObj(symbolName);
                addToTable(schemeSymbol, nextIndex);
                incrementFillSize();
                doRehashIfRequired();

                return schemeSymbol;
            }

            // in case the slot is occupied: check if object in slot is searched symbol
            symbol = symbolTable[nextIndex];
            if (symbol.getValue().equals(symbolName)) {
                // return symbol in slot 'nextIndex'
                return symbol;
            }

            // if the symbol at slot 'nextIndex' is not the symbol we searched for, we have
            // a hash collision > store new symbol at next slot available
            nextIndex = ++nextIndex % tableSize;

            // if there's no free slot available in the table, throw error
            if (nextIndex == startIndex) {
                SchemeError.print("Symbol table problem!");
            }
        }
    }

    private void doRehashIfRequired() {
        if (tableFillSize > 0.75 * tableSize) {
            startRehash();
        }
    }

    private void startRehash() {
        // TODO implement rehash
    }


    private void incrementFillSize() {
        tableFillSize++;
    }


    private boolean isFreeSlot(int slotNum) {
        if (slotNum > 0 && slotNum < tableSize) {
            return Objects.isNull(symbolTable[slotNum]) ? true : false;
        }
        throw new IllegalArgumentException(String.format("Symbol symbolTable index must be between %d and %d!", 0,
                (tableSize - 1)));
    }

    private void addToTable(SchemeSymbol symbol, int slotNum) {
        symbolTable[slotNum] = symbol;
    }


}
