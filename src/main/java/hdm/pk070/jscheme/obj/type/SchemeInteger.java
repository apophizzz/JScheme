package hdm.pk070.jscheme.obj.type;

import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * @author patrick.kleindienst
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
