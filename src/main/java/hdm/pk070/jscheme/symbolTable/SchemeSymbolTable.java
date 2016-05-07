package hdm.pk070.jscheme.symbolTable;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.hash.HashAlgProvider;
import hdm.pk070.jscheme.hash.impl.StandardHashAlgProvider;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 *
 */
public class SchemeSymbolTable {

    private static final Logger LOGGER = LogManager.getLogger(SchemeSymbolTable.class.getName());
    public static final int INITIAL_SYMBOL_TABLE_SIZE = 511;

    private static int currentTableSize = INITIAL_SYMBOL_TABLE_SIZE;
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
        this.symbolTable = new SchemeSymbol[currentTableSize];
        this.tableFillSize = 0;
    }


    public SchemeSymbol getOrAdd(String symbolName) throws SchemeError {
        Objects.requireNonNull(symbolName);

        int nextIndex;
        int hashVal = hashAlgProvider.computeHash(symbolName);
        int startIndex = hashVal % currentTableSize;
        SchemeSymbol symbol;
        nextIndex = startIndex;

        for (; ; ) {

            // In case the current index marks a free slot
            if (isFreeSlot(nextIndex)) {
                SchemeSymbol schemeSymbol = new SchemeSymbol(symbolName);
                addToTable(schemeSymbol, nextIndex);
                incrementFillSize();
                doRehashIfRequired();

                return schemeSymbol;
            }

            // in case the slot is occupied: check if object in slot is searched symbol
            symbol = symbolTable[nextIndex];
            if (symbol.getValue().equals(symbolName)) {
                // return symbol in slot 'nextIndex'
                LOGGER.debug(String.format("Symbol '%s' already present in symbol table, gets returned", symbol
                        .toString()));
                return symbol;
            }

            // if the symbol at slot 'nextIndex' is not the symbol we searched for, we have
            // a hash collision > store new symbol at next slot available
            nextIndex = ++nextIndex % currentTableSize;

            // if there's no free slot available in the table, throw error
            if (nextIndex == startIndex) {
                throw new SchemeError("Symbol table problem!");
            }
        }
    }

    private void doRehashIfRequired() throws SchemeError {
        if (tableFillSize > 0.75 * currentTableSize) {
            LOGGER.debug("Rehash initiated ...");
            startRehash();
        }
    }

    private void startRehash() throws SchemeError {
        int oldTableSize = currentTableSize;
        LOGGER.debug(String.format("Rehash: Old symbol table size is %d", oldTableSize));

        int newTableSize = getNextPowerOfTwoMinusOne();
        currentTableSize = newTableSize;
        LOGGER.debug(String.format("Rehash: New table size is %d", newTableSize));

        SchemeSymbol[] oldSymbolTable = symbolTable;
        symbolTable = new SchemeSymbol[newTableSize];

        for (int oldTableIndex = 0; oldTableIndex < oldTableSize; oldTableIndex++) {
            SchemeSymbol oldSymbol = oldSymbolTable[oldTableIndex];

            // in case oldSymbol is not null
            if (Objects.nonNull(oldSymbol)) {
                // re-compute hash
                int hash = hashAlgProvider.computeHash(oldSymbol.getValue());
                int startIndex = hash % newTableSize;
                int nextIndex = startIndex;

                // same old story: search for free slot
                for (; ; ) {

                    if (isFreeSlot(nextIndex)) {
                        // if slot is free: add symbol and end loop
                        symbolTable[nextIndex] = oldSymbol;
                        break;
                    }

                    // increment search index if slot is occupied
                    nextIndex = ++nextIndex % newTableSize;

                    // if the whole table has been searched, there's no free slot > error!
                    if (nextIndex == startIndex) {
                        // switch back to old table in case of there's no free slot
                        symbolTable = oldSymbolTable;
                        currentTableSize = oldTableSize;
                        LOGGER.debug("Symbol table error. No free slot found!");
                        throw new SchemeError("Symbol table problem!");
                    }
                }

            }
        }
    }

    private int getNextPowerOfTwoMinusOne() {
        return (currentTableSize + 1) * 2 - 1;
    }

    private void incrementFillSize() {
        tableFillSize++;
    }


    private boolean isFreeSlot(int slotNum) {
        if (slotNum > 0 && slotNum < currentTableSize) {
            return Objects.isNull(symbolTable[slotNum]) ? true : false;
        }
        throw new IllegalArgumentException(String.format("symbolTable index must be between %d and %d!", 0,
                (currentTableSize - 1)));
    }

    private void addToTable(SchemeSymbol symbol, int slotNum) {
        symbolTable[slotNum] = symbol;
        LOGGER.debug(String.format("Symbol '%s' has been added to symbol table", symbol.toString()));
    }


}
