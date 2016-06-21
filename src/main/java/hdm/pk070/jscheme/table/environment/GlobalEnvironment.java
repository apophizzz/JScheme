package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.obj.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.ResizableTable;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

import java.util.Objects;

/**
 * This class defines the JScheme global environment. The global environment's job is to store bindings between
 * {@link SchemeSymbol}s like 'abc' and their values (of type string, number, ...). According to the class' name,
 * these bindings are always visible and can be accessed by every Scheme function.
 */
public final class GlobalEnvironment extends ResizableTable<SchemeSymbol, EnvironmentEntry> implements
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
    protected EnvironmentEntry handleDuplicateEntries(final EnvironmentEntry newEntry, final EnvironmentEntry
            oldEntry, int
                                                              oldEntryIndex) {
        ((EnvironmentEntry) entries[oldEntryIndex]).setValue(newEntry.getValue());
        return (EnvironmentEntry) entries[oldEntryIndex];
    }

    @Override
    protected int keyToHashVal(final SchemeSymbol key) {
        return key.hashCode();
    }

    @Override
    protected int valueToHashVal(final EnvironmentEntry value) {
        return value.hashCode();
    }

    @Override
    protected boolean keysMatch(final SchemeSymbol key, final EnvironmentEntry entryFound) {
        return key == entryFound.getKey();
    }

    @Override
    protected boolean entriesMatch(final EnvironmentEntry entryToAdd, final EnvironmentEntry existingEntry) {
        return existingEntry.equals(entryToAdd);
    }

    @Override
    public String toString() {
        return String.format("Global Env [id: %d] ", this.hashCode());
    }
}
