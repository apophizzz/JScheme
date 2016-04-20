package hdm.pk070.jscheme.obj.type;

import hdm.pk070.jscheme.obj.SchemeObject;

import java.util.Objects;

/**
 * Created by patrick on 19.04.16.
 */
public class SchemeString extends SchemeObject {


    private String stringVal;

    public static SchemeString createObj(String stringVal) {
        return new SchemeString(stringVal);
    }

    protected SchemeString(String stringVal) {
        Objects.requireNonNull(stringVal);
        this.stringVal = stringVal;
    }


    @Override
    public String getValue() {
        return stringVal;
    }
}
