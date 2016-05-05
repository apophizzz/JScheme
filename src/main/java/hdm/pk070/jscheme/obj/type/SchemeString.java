package hdm.pk070.jscheme.obj.type;

import hdm.pk070.jscheme.obj.SchemeObject;

import java.util.Objects;

/**
 * @author patrick.kleindienst
 */
public final class SchemeString extends SchemeObject {


    private String stringVal;

    public SchemeString(String stringVal) {
        Objects.requireNonNull(stringVal);
        this.stringVal = stringVal;
    }


    @Override
    public String getValue() {
        return stringVal;
    }

    @Override
    public String toString() {
        return stringVal;
    }
}
