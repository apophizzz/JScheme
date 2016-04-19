package hdm.pk070.jscheme.obj.type;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.tag.Tag;

/**
 * Created by patrick on 19.04.16.
 */
public class SchemeInteger extends SchemeObject {


    private int intVal;

    public static SchemeInteger createObj(int intVal) {
        return new SchemeInteger(intVal);
    }

    private SchemeInteger(int intVal) {
        super(Tag.T_INTEGER);
        this.intVal = intVal;
    }

    public int getIntVal() {
        return intVal;
    }
}
