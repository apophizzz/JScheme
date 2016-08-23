package hdm.pk070.jscheme.table;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.table.environment.GlobalEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.Optional;

/**
 * An abstract implementation for hash tables which can grow in size dynamically by rehashing their contents. A concrete
 * example is {@link GlobalEnvironment}. This implementation employs a closed hash.
 *
 * @author patrick.kleindienst
 */

@SuppressWarnings("unchecked")
public abstract class ResizableTable<KEY, VALUE> extends BaseTable<KEY, VALUE> {


    private static final int INITIAL_TABLE_SIZE = 511;

    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    private int currentTableSize = INITIAL_TABLE_SIZE;
    private int currentFillSize = 0;

    protected Object[] entries = new Object[INITIAL_TABLE_SIZE];

    @Override
    public Optional<VALUE> get(final KEY key) {
        Objects.requireNonNull(key);

        // Compute hash out of key param
        int hash = keyToHashVal(key);

        // Compute start index with hash (avoid negative hash values!)
        int startIndex = (hash & 0x7FFFFFFF) % currentTableSize;

        // Start searching value at startIndex
        int nextIndex = startIndex;
        LOGGER.debug(String.format("Start searching for entry with key %s at index %d", key.toString(), nextIndex));

        // Jump into infinite loop
        for (; ; ) {

            // Check if an entry exists at current index
            if (Objects.nonNull(entries[nextIndex])) {
                VALUE entryFound = (VALUE) entries[nextIndex];
                if (keysMatch(key, entryFound)) {
                    LOGGER.debug(String.format("Found entry %s for key %s at index %d", entryFound.toString(), key
                            .toString(), nextIndex));
                    return Optional.of(entryFound);
                }

                // Keys did not match, proceed with next index
            } else {
                // No entry found for specified key -> return empty result
                LOGGER.debug(String.format("No entry found for key %s", key.toString()));
                return Optional.empty();
            }

            // Entry found was not null but didn't match -> try next
            nextIndex = ++nextIndex % currentTableSize;

            if (reachedStartIndexAgain(nextIndex, startIndex)) {
                LOGGER.debug(String.format("Searched whole table but didn't find entry for key %s", key.toString()));
                return Optional.empty();
            }
        }
    }

    @Override
    public VALUE add(final VALUE value) throws SchemeError {
        Objects.nonNull(value);

        // Compute hash
        int hash = valueToHashVal(value);

        // Compute start index
        int startIndex = (hash & 0x7FFFFFFF) % currentTableSize;

        // We search at nextIndex, starting with startIndex
        int nextIndex = startIndex;

        // Enter infinite loop
        for (; ; ) {

            // Check if entry at current index already exists
            if (entryExistsAt(nextIndex)) {
                if (entriesMatch(value, (VALUE) entries[nextIndex])) {
                    // Let subclass decide what to do in this case
                    return handleDuplicateEntries(value, (VALUE) entries[nextIndex], nextIndex);
                }
            } else {
                // Entry does not yet exist
                entries[nextIndex] = value;
                incrementFillSize();
                checkFillSizeAndRehashIfRequired();

                // End here
                return value;
            }

            nextIndex = ++nextIndex % currentTableSize;

            // Throw fatal error if there's no free slot available
            if (reachedStartIndexAgain(nextIndex, startIndex)) {
                throw new SchemeError("Fatal error!");
            }

        }
    }

    private boolean reachedStartIndexAgain(int next, int start) {
        return next == start;
    }

    private void checkFillSizeAndRehashIfRequired() throws SchemeError {
        if (currentFillSize > 0.75 * currentTableSize) {
            doRehash();
        }
    }

    private void doRehash() throws SchemeError {
        // Save original table size
        int oldTableSize = currentTableSize;

        // Compute new table size
        int newTableSize = nextPowerOfTwoMinusOne();

        // Store reference to original entry list
        Object[] oldEntries = entries;

        // Create new entry list with new size
        Object[] newEntries = new Object[newTableSize];

        // Update references
        entries = newEntries;
        currentTableSize = newTableSize;

        // Start iterating over old list
        for (int oldTableIndex = 0; oldTableIndex < oldEntries.length; oldTableIndex++) {
            VALUE oldEntry = (VALUE) oldEntries[oldTableIndex];

            // If entry exists, copy it to new list
            if (Objects.nonNull(oldEntry)) {
                // Compute new hash
                int newHash = valueToHashVal(oldEntry);
                int startIndex = newHash % newTableSize;
                int nextIndex = startIndex;

                // Start searching for free slot in new table
                for (; ; ) {
                    if (!entryExistsAt(nextIndex)) {
                        // If there's a free slot at current index, add old entry
                        newEntries[nextIndex] = oldEntry;
                        // We're done here. Exit searching loop.
                        break;
                    }

                    // If slot is already occupied -> increment index
                    nextIndex = ++nextIndex % newTableSize;

                    // Check if whole new table has been searched
                    if (reachedStartIndexAgain(nextIndex, startIndex)) {
                        // restore original references
                        entries = oldEntries;
                        currentTableSize = oldTableSize;

                        // throw fatal error
                        throw new SchemeError(String.format("%s: Fatal error during rehash", this.getClass()
                                .getSimpleName()));
                    }
                }
            }
        }


    }

    private int nextPowerOfTwoMinusOne() {
        return (currentTableSize + 1) * 2 - 1;
    }

    private void incrementFillSize() {
        currentFillSize++;
    }

    private boolean entryExistsAt(int index) {
        return Objects.nonNull(entries[index]);
    }

    /**
     * It's left to subclasses how duplicated entries should be handled.
     *
     * @param newEntry
     *         The entry which shall be inserted into table.
     * @param oldEntry
     *         Existing entry which has same value as {@code newEntry}.
     * @param oldEntryIndex
     *         The table index of the existing entry.
     * @return The value which shall be valid. The decision is up to the subclass.
     */
    protected abstract VALUE handleDuplicateEntries(final VALUE newEntry, final VALUE oldEntry, int oldEntryIndex);

    /**
     * It's left to subclasses how {@code key} shall be transformed into a hash. This operation is applied when
     * searching an element within the table by a key.
     *
     * @param key
     *         The key which shall be applied in order to compute a hash value.
     * @return The computed hash.
     */
    protected abstract int keyToHashVal(final KEY key);

    /**
     * It's left to subclasses how {@code value} shall be transformed into a hash. This operation is applied when
     * adding an element to the table.
     *
     * @param value
     *         The value which shall be applied in order to compute a hash value.
     * @return The computed hash.
     */
    protected abstract int valueToHashVal(final VALUE value);

    /**
     * It's left to subclasses when to entries shall be considered equal.
     *
     * @param entryToAdd
     *         The entry which should be added to the table.
     * @param existingEntry
     *         An entry which already exists within the table.
     * @return True if equal, false otherwise.
     */
    protected abstract boolean entriesMatch(final VALUE entryToAdd, final VALUE existingEntry);

}
