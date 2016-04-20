package hdm.pk070.jscheme.obj.type;

import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * Created by patrick on 19.04.16.
 */
public class SchemeInteger extends SchemeObject {


    private int intVal;

    public static SchemeInteger createObj(int intVal) {
        return new SchemeInteger(intVal);
    }

    private SchemeInteger(int intVal) {
        this.intVal = intVal;
    }


    @Override
    public Integer getValue() {
        return intVal;
    }
}
