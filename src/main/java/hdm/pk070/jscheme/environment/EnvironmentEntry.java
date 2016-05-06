package hdm.pk070.jscheme.environment;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;

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
}
