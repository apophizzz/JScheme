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

    private static GlobalEnvironment globalEnvironment = null;
    private static int currentGlobalEnvSize = INITIAL_GLOBAL_ENV_SIZE;

    public static GlobalEnvironment getInstance() {
        if (Objects.isNull(globalEnvironment)) {
            globalEnvironment = new GlobalEnvironment();
        }
        return globalEnvironment;
    }


    private GlobalEnvironment() {
    }

    public SchemeObject get(SchemeSymbol expression) {
        // TODO: implementation
        return null;
    }

    public void put(SchemeSymbol envKey, SchemeObject envValue) {
        // 1. Create numeric key based on envKey (maybe hashcode?)
        // 2. Compute startIndex = key (step 1) % current size of global env
        // 3. Before searching the next free slot, set nextIndex = startIndex (see step 2)
        return;
    }
}
