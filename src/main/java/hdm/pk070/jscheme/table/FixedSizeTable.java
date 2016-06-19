package hdm.pk070.jscheme.table;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by patrick on 16.05.16.
 */
public abstract class FixedSizeTable<KEY, VALUE> extends BaseTable<KEY, VALUE> {

    private Object[] entries;

    protected FixedSizeTable(int fixedSize) {
        this.entries = new Object[fixedSize];
    }

    public Optional<VALUE> get(KEY key) {
        Objects.requireNonNull(key);

        // Iteration über alle Slots
        for (Object entry : entries) {
            // extrahiere und vergleiche Key
            if (Objects.nonNull(entry) && keysMatch(key, (VALUE) entry)) {
                return Optional.of((VALUE) entry);
            }
        }
        return Optional.empty();
    }

    public VALUE add(VALUE value) throws SchemeError {
        for (int i = 0; i < entries.length; i++) {
            if (Objects.nonNull(entries[i])) {
                if ((((EnvironmentEntry) value).getKey() == ((EnvironmentEntry)
                        entries[i]).getKey())) {
                    entries[i] = value;
                    return value;
                }
            } else {
                entries[i] = value;
                return value;
            }
        }
        throw new SchemeError("FixedSizeTable overflow!");
    }

}
