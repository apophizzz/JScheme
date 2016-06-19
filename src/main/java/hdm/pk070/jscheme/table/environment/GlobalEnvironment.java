package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import hdm.pk070.jscheme.table.ResizableTable;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

import java.util.Objects;

/**
 * This class defines the JScheme global environment. The global environment's job is to store bindings between
 * {@link SchemeSymbol}s like 'abc' and their values (of type string, number, ...).
 */
public class GlobalEnvironment extends ResizableTable<SchemeSymbol, EnvironmentEntry> implements
        Environment<SchemeSymbol, EnvironmentEntry> {


    private static GlobalEnvironment globalEnvironment = null;


    public static GlobalEnvironment getInstance() {
        if (Objects.isNull(globalEnvironment)) {
            globalEnvironment = new GlobalEnvironment();
        }
        return globalEnvironment;
    }


    private GlobalEnvironment() {
    }


    @Override
    protected EnvironmentEntry handleDuplicateEntries(EnvironmentEntry newEntry, EnvironmentEntry oldEntry, int
            oldEntryIndex) {
        ((EnvironmentEntry) entries[oldEntryIndex]).setValue(newEntry.getValue());
        return (EnvironmentEntry) entries[oldEntryIndex];
    }

    @Override
    protected int keyToHashVal(SchemeSymbol key) {
        return key.hashCode();
    }

    @Override
    protected int valueToHashVal(EnvironmentEntry value) {
        return value.hashCode();
    }

    @Override
    protected boolean keysMatch(SchemeSymbol key, EnvironmentEntry entryFound) {
        return key == entryFound.getKey();
    }

    @Override
    protected boolean entriesMatch(EnvironmentEntry entryToAdd, EnvironmentEntry existingEntry) {
        return existingEntry.equals(entryToAdd);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
