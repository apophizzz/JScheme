package hdm.pk070.jscheme.environment;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;

import java.util.Objects;

/**
 * Created by patrick on 05.05.16.
 */
class EnvironmentEntry {

    private SchemeSymbol key;
    private SchemeObject value;

    static EnvironmentEntry create(SchemeSymbol key, SchemeObject value) {
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

    public void setKey(SchemeSymbol key) {
        Objects.requireNonNull(key);
        this.key = key;
    }

    public void setValue(SchemeObject value) {
        Objects.requireNonNull(value);
        this.value = value;
    }
}
