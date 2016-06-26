package hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex;

import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;

/**
 * @author patrick.kleindienst
 */
public final class SchemeFloat extends SchemeFloatComplex {

    private float floatVal;

    public SchemeFloat(float floatVal) {
        this.floatVal = floatVal;
    }

    @Override
    public SchemeNumber add(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return new SchemeFloat(this.getValue() + ((SchemeInteger) number).getValue());
        }
        return new SchemeFloat(this.getValue() + ((SchemeFloat) number).getValue());
    }

    @Override
    public SchemeNumber subtract(SchemeNumber number) {
        return null;
    }

    @Override
    public Float getValue() {
        return floatVal;
    }

    @Override
    public String toString() {
        return String.valueOf(floatVal);
    }
}
