package hdm.pk070.jscheme.obj.builtin.simple.number.exact;

import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloatComplex;

/**
 * @author patrick.kleindienst
 */
public final class SchemeInteger extends SchemeExactNumber {


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

    @Override
    public SchemeNumber add(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return new SchemeInteger(this.getValue() + ((SchemeInteger) number).getValue());
        }
        return new SchemeFloat(this.getValue() + ((SchemeFloat) number).getValue());
    }

    @Override
    public SchemeNumber subtract(SchemeNumber number) {
        float a = 0;
        int b = 1;

        System.out.println(a + b);

        return null;
    }
}
