package hdm.pk070.jscheme.obj.type;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.tag.Tag;

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
        super(Tag.T_STRING);
        Objects.requireNonNull(stringVal);
        this.stringVal = stringVal;
    }

    public String getStringVal() {
        return stringVal;
    }
}
