package hdm.pk070.jscheme.obj.builtin.simple;

import hdm.pk070.jscheme.obj.SchemeObject;

import java.util.Objects;

/**
 * A JScheme string.
 *
 * @author patrick.kleindienst
 */
public final class SchemeString extends SchemeObject {


    private final String stringVal;

    public SchemeString(final String stringVal) {
        Objects.requireNonNull(stringVal);
        this.stringVal = stringVal;
    }


    @Override
    public String getValue() {
        return stringVal;
    }

    @Override
    public String toString() {
        return "\"" + stringVal + "\"";
    }
}
