package hdm.pk070.jscheme.table.environment.entry;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;

import java.util.Objects;

/**
 * Created by patrick on 05.05.16.
 */
public class EnvironmentEntry {

    private SchemeSymbol key;
    private SchemeObject value;

    public static EnvironmentEntry create(SchemeSymbol key, SchemeObject value) {
        return new EnvironmentEntry(key, value);
    }

    private EnvironmentEntry(SchemeSymbol key, SchemeObject value) {
        this.key = key;
        this.value = value;
    }

    public SchemeSymbol getKey() {
        return key;
    }

    public SchemeObject getValue() {
        return value;
    }

    public void setValue(SchemeObject value) {
        Objects.requireNonNull(value);
        this.value = value;
    }


    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        }
        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        // CAUTION: Two environment entries can only be considered equal if their keys both
        // point to exactly the same SchemeSymbol from symbol table!
        if (!(key == ((EnvironmentEntry) obj).getKey())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
