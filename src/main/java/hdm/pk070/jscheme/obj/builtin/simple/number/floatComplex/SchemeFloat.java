package hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex;

import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeExactNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeFraction;
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
    public Float getValue() {
        return floatVal;
    }

    @Override
    public SchemeNumber add(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return number.add(this);
        } else if (number.typeOf(SchemeFloat.class)) {
            return new SchemeFloat(this.getValue() + ((SchemeFloat) number).getValue());
        } else {
            // number is of type SchemeFraction
            return this.add(((SchemeFraction) number).toSchemeFloat());
        }
    }

    @Override
    public SchemeNumber subtract(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return new SchemeFloat(this.getValue() - ((SchemeInteger) number).getValue());
        } else if (number.typeOf(SchemeFloat.class)) {
            return new SchemeFloat(this.getValue() - ((SchemeFloat) number).getValue());
        } else {
            // number is of type SchemeFraction
            return this.subtract(((SchemeFraction) number).toSchemeFloat());
        }
    }

    @Override
    public SchemeNumber multiply(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return number.multiply(this);
        } else if (number.typeOf(SchemeFloat.class)) {
            return new SchemeFloat(this.getValue() * ((SchemeFloat) number).getValue());
        }
        // number is of type SchemeFraction
        return this.multiply(((SchemeFraction) number).toSchemeFloat());
    }

    @Override
    public SchemeNumber divide(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return new SchemeFloat(this.getValue() / ((SchemeInteger) number).getValue());
        } else if (number.typeOf(SchemeFloat.class)) {
            return new SchemeFloat(this.getValue() / ((SchemeFloat) number).getValue());
        }
        // number is of type SchemeFraction
        return this.divide(((SchemeFraction) number).toSchemeFloat());
    }

    @Override
    public SchemeNumber absolute() {
        if (this.getValue() > 0) {
            return new SchemeFloat(this.getValue());
        } else {
            return this.multiply(new SchemeInteger(-1));
        }
    }

    @Override
    public String toString() {
        return String.valueOf(floatVal);
    }
}
