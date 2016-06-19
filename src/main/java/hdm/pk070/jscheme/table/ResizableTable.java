package hdm.pk070.jscheme.table;

import hdm.pk070.jscheme.error.SchemeError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by patrick on 08.05.16.
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

        // 1. Extract integer hash out of key param
        int hash = keyToHashVal(key);

        // 2. Compute start index with hash
        int startIndex = hash % currentTableSize;

        // 3. Start searching value at startIndex
        int nextIndex = startIndex;
        LOGGER.debug(String.format("Start searching for entry with key %s at index %d", key.toString(), nextIndex));

        // 4. Jump into infinite loop
        for (; ; ) {

            // 5. Check if an entry exists at current index
            if (Objects.nonNull(entries[nextIndex])) {
                VALUE entryFound = (VALUE) entries[nextIndex];
                if (keysMatch(key, entryFound)) {
                    LOGGER.debug(String.format("Found entry %s for key %s at index %d", entryFound.toString(), key
                            .toString(), nextIndex));
                    return Optional.of(entryFound);
                }

                // 6. Keys did not match, proceed with next index
            } else {
                // 7. No entry found for specified key -> Return null.
                LOGGER.debug(String.format("No entry found for key %s", key.toString()));
                return Optional.empty();
            }

            // 8. Entry found was not null but didn't match -> try next
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
        int startIndex = hash % currentTableSize;

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
        return Objects.nonNull(entries[index]) ? true : false;
    }

    protected abstract VALUE handleDuplicateEntries(final VALUE newEntry, final VALUE oldEntry, int oldEntryIndex);

    protected abstract int keyToHashVal(final KEY key);

    protected abstract int valueToHashVal(final VALUE value);

    protected abstract boolean entriesMatch(final VALUE entryToAdd, final VALUE existingEntry);

}
