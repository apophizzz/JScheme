package hdm.pk070.jscheme.table;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by patrick on 16.05.16.
 */

@SuppressWarnings("unchecked")
public abstract class FixedSizeTable<KEY, VALUE> extends BaseTable<KEY, VALUE> {

    private final Object[] entries;

    protected FixedSizeTable(int fixedSize) {
        this.entries = new Object[fixedSize];
    }

    @Override
    public Optional<VALUE> get(final KEY key) {
        Objects.requireNonNull(key);

        for (Object entry : entries) {
            if (Objects.nonNull(entry) && keysMatch(key, (VALUE) entry)) {
                return Optional.of((VALUE) entry);
            }
        }
        return Optional.empty();
    }

    @Override
    public VALUE add(final VALUE value) throws SchemeError {
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
