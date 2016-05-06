package hdm.pk070.jscheme.environment;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;

import java.util.Objects;

/**
 * This class defines the JScheme global environment. The global environment's job is to store bindings between
 * {@link SchemeSymbol}s like 'abc' and their values (of type string, number, ...).
 */
public class GlobalEnvironment {

    private static final int INITIAL_GLOBAL_ENV_SIZE = 511;
    private static int currentGlobalEnvSize = INITIAL_GLOBAL_ENV_SIZE;
    private static GlobalEnvironment globalEnvironment = null;

    private EnvironmentEntry[] environmentEntries = new EnvironmentEntry[INITIAL_GLOBAL_ENV_SIZE];

    public static GlobalEnvironment getInstance() {
        if (Objects.isNull(globalEnvironment)) {
            globalEnvironment = new GlobalEnvironment();
        }
        return globalEnvironment;
    }


    private GlobalEnvironment() {
    }

    public SchemeObject get(SchemeSymbol envKey) {
        // 1. Extract int hash out of envKey
        int hash = envKey.hashCode();

        // 2. Compute startIndex
        int startIndex = hash % currentGlobalEnvSize;

        // 3. Start searching with nextIndex = startIndex
        int nextIndex = startIndex;
        SchemeSymbol keyFound;

        // 4. Jump into infinite loop
        for (; ; ) {
            // 5. Look at env entry at index 'nextIndex'. If envKey and stored key reference
            // the same env entry, then return its value
            if (Objects.nonNull(environmentEntries[nextIndex])) {
                keyFound = environmentEntries[nextIndex].getKey();

                // TODO: check if comparison by reference or by equals is reasonable
                if (keyFound == envKey) {
                    return environmentEntries[nextIndex].getValue();
                }

                // 6. If the key found at nextIndex is null, there requested entry does not exist
                if (Objects.isNull(keyFound)) {
                    return null;
                }
            }

            // 7. Compute next index to be checked
            nextIndex = (nextIndex + 1) % currentGlobalEnvSize;

            // 8. If nextIndex again reaches the startIndex, we've searched the whole env without success
            if (nextIndex == startIndex) {
                // searched entry does not exist
                return null;
            }
        }

    }

    public void put(SchemeSymbol envKey, SchemeObject envValue) {
        // TODO: implementation

        // 1. Create numeric key based on envKey (maybe hashcode?)
        // 2. Compute startIndex = key (step 1) % current size of global env
        // 3. Before searching the next free slot, set nextIndex = startIndex (see step 2)
        return;
    }
}
