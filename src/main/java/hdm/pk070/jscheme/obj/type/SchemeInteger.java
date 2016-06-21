package hdm.pk070.jscheme.obj.type;

import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * @author patrick.kleindienst
 */
public final class SchemeInteger extends SchemeObject {


    private final int intVal;

    public SchemeInteger(final int intVal) {
        this.intVal = intVal;
    }


    @Override
    public Integer getValue() {
        return intVal;
    }

    @Override
    public String toString() {
        return String.valueOf(intVal);
    }
}
