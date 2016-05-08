package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * This class defines the JScheme global environment. The global environment's job is to store bindings between
 * {@link SchemeSymbol}s like 'abc' and their values (of type string, number, ...).
 */
public class GlobalEnvironment {

    private static final Logger LOGGER = LogManager.getLogger("hdm.pk070.jscheme.GlobalEnvLogger");

    private static final int INITIAL_GLOBAL_ENV_SIZE = 511;
    private static int currentGlobalEnvironmentSize = INITIAL_GLOBAL_ENV_SIZE;
    private static GlobalEnvironment globalEnvironment = null;

    private EnvironmentEntry[] currentGlobalEnvironmentEntries = new EnvironmentEntry[INITIAL_GLOBAL_ENV_SIZE];
    private int currentFillSize;

    public static GlobalEnvironment getInstance() {
        if (Objects.isNull(globalEnvironment)) {
            globalEnvironment = new GlobalEnvironment();
        }
        return globalEnvironment;
    }


    private GlobalEnvironment() {
        currentFillSize = 0;
    }

    // TODO: Replace return type with Optional<SchemeObject>
    public SchemeObject get(SchemeSymbol envKey) {
        LOGGER.debug(String.format("Requesting environment entry with key: %s", envKey.toString()));

        // 1. Extract int hash out of envKey
        int hash = envKey.hashCode();

        // 2. Compute startIndex
        int startIndex = hash % currentGlobalEnvironmentSize;

        // 3. Start searching with nextIndex = startIndex
        int nextIndex = startIndex;
        SchemeSymbol keyFound;

        // 4. Jump into infinite loop
        for (; ; ) {
            // 5. Look at env entry at index 'nextIndex'. If envKey and stored key reference
            // the same env entry, then return its value
            if (Objects.nonNull(currentGlobalEnvironmentEntries[nextIndex])) {
                keyFound = currentGlobalEnvironmentEntries[nextIndex].getKey();

                // Return value if both keys are the same object, continue searching otherwise
                if (keyFound == envKey) {
                    SchemeObject envValue = currentGlobalEnvironmentEntries[nextIndex].getValue();
                    LOGGER.debug(String.format("Found entry with key %s. Return value %s", envKey.toString(),
                            envValue.toString()));
                    return envValue;
                }

                LOGGER.debug(String.format("Keys under specified index %d did not match. Proceed with next index.",
                        nextIndex));

            } else {
                // 6. If the env entry found at nextIndex is null, there requested entry does not exist
                LOGGER.debug(String.format("Unable to find entry by key %s. Return null.", envKey.toString()));
                return null;
            }

            // 7. Compute next index to be checked
            nextIndex = (++nextIndex) % currentGlobalEnvironmentSize;

            // 8. If nextIndex again reaches the startIndex, we've searched the whole env without success
            if (nextIndex == startIndex) {
                // searched entry does not exist
                LOGGER.debug(String.format(""));
                return null;
            }
        }

    }

    public void put(SchemeSymbol envKey, SchemeObject envValue) throws SchemeError {
        // 1. Create numeric key based on envKey (maybe hashcode?)
        int hash = envKey.hashCode();

        // 2. Compute startIndex = key (step 1) % current size of global env
        int startIndex = hash % currentGlobalEnvironmentSize;

        // 3. Before searching the next free slot, set nextIndex = startIndex (see step 2)
        int nextIndex = startIndex;
        SchemeSymbol keyFound;

        for (; ; ) {

            if (Objects.nonNull(currentGlobalEnvironmentEntries[nextIndex])) {
                // 4. Entry already exists, overwrite

                keyFound = currentGlobalEnvironmentEntries[nextIndex].getKey();
                if (keyFound == envKey) {
                    currentGlobalEnvironmentEntries[nextIndex].setValue(envValue);
                    // 5. End here
                    return;
                }
            }

            if (Objects.isNull(currentGlobalEnvironmentEntries[nextIndex])) {
                // 6. Entry does not exist yet - store new entry
                currentGlobalEnvironmentEntries[nextIndex] = EnvironmentEntry.create(envKey, envValue);

                // 7. Increment fill size
                currentFillSize++;

                // 8. Check current fill size and initiate rehash if necessary
                checkFillSizeAndStartRehashIfRequired();

                // 9. End here
                return;
            }


            // 10. Increment next index if the wrong key has been found
            nextIndex = (++nextIndex) % currentGlobalEnvironmentSize;

            // 11. If nextIndex reaches startIndex again, no free slot could have been found -> throw error
            if (nextIndex == startIndex) {
                throw new SchemeError("Global environment crammed!");
            }

        }

    }

    private void checkFillSizeAndStartRehashIfRequired() throws SchemeError {
        if (currentFillSize > 0.75 * currentGlobalEnvironmentSize) {
            startRehash();
        }
    }

    private void startRehash() throws SchemeError {
        // Save old size
        int oldGlobalEnvSize = currentGlobalEnvironmentSize;

        // Compute new size (next power of two minus 1)
        int newGlobalEnvSize = getNextPowerOfTwoMinusOne();

        // Store reference to old environment
        EnvironmentEntry[] oldGlobalEnvEntries = currentGlobalEnvironmentEntries;

        // Create array for new environment
        EnvironmentEntry[] newGlobalEnvEntries = new EnvironmentEntry[newGlobalEnvSize];

        // update old settings (must be undone in case of error!)
        currentGlobalEnvironmentEntries = newGlobalEnvEntries;
        currentGlobalEnvironmentSize = newGlobalEnvSize;

        // Copy entries from old env array to new array
        for (int oldEnvIndex = 0; oldEnvIndex < oldGlobalEnvEntries.length; oldEnvIndex++) {
            EnvironmentEntry oldEnvEntry = oldGlobalEnvEntries[oldEnvIndex];

            // If an env entry exists at index 'oldEnvIndex', search new free slot
            if (Objects.nonNull(oldEnvEntry)) {
                int newHash = oldEnvEntry.getKey().hashCode();
                int startIndex = newHash % newGlobalEnvSize;
                int nextIndex = startIndex;

                for (; ; ) {
                    EnvironmentEntry entryAtCurrentSlot;
                    entryAtCurrentSlot = newGlobalEnvEntries[nextIndex];

                    // if slot is still free, copy old entry to it
                    if (Objects.isNull(entryAtCurrentSlot)) {
                        newGlobalEnvEntries[nextIndex] = oldEnvEntry;
                        // slot found, break loop and move on to next entry
                        break;
                    }

                    // If slot is already occupied, we have a hash collision -> search for next
                    // free slot available (closed hash)
                    nextIndex = ++nextIndex % newGlobalEnvSize;

                    // If 'nextIndex' again reaches 'startIndex', there's no free slot
                    // available -> throw error
                    if (nextIndex == startIndex) {
                        currentGlobalEnvironmentEntries = oldGlobalEnvEntries;
                        currentGlobalEnvironmentSize = oldGlobalEnvSize;
                        throw new SchemeError("Global environment rehash error. No free slot available.");
                    }
                }
            }
        }
    }

    private int getNextPowerOfTwoMinusOne() {
        return (currentGlobalEnvironmentSize + 1) * 2 - 1;
    }
}
