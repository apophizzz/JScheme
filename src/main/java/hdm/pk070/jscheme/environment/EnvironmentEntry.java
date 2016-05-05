package hdm.pk070.jscheme.environment;

import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * Created by patrick on 05.05.16.
 */
class EnvironmentEntry {

    private SchemeObject key;
    private SchemeObject value;

    static EnvironmentEntry create(SchemeObject key, SchemeObject value) {
        return new EnvironmentEntry(key, value);
    }

    private EnvironmentEntry(SchemeObject key, SchemeObject value) {
        this.key = key;
        this.value = value;
    }
}
