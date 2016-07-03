package hdm.pk070.jscheme.obj.builtin.simple.number.exact;

import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import org.apache.commons.math3.fraction.Fraction;

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
    public SchemeNumber add(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return new SchemeInteger(this.getValue() + ((SchemeInteger) number).getValue());
        } else if (number.typeOf(SchemeFloat.class)) {
            return new SchemeFloat(this.getValue() + ((SchemeFloat) number).getValue());
        } else {
            // number is of type SchemeFraction
            return number.add(this);
        }
    }

    @Override
    public SchemeNumber subtract(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return new SchemeInteger(this.getValue() - ((SchemeInteger) number).getValue());
        } else if (number.typeOf(SchemeFloat.class)) {
            return new SchemeFloat(this.getValue() - ((SchemeFloat) number).getValue());
        } else {
            // number is of type SchemeFraction
            return new SchemeFraction(new Fraction(this.getValue()).subtract(((SchemeFraction) number).getValue()));
        }
    }

    @Override
    public SchemeNumber multiply(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return new SchemeInteger(this.getValue() * ((SchemeInteger) number).getValue());
        } else if (number.typeOf(SchemeFloat.class)) {
            return new SchemeFloat(this.getValue() * ((SchemeFloat) number).getValue());
        } else {
            // number is of type SchemeFraction
            return number.multiply(this);
        }
    }

    @Override
    public SchemeNumber divide(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return new SchemeFraction(this.getValue(), ((SchemeInteger) number).getValue());
        } else if (number.typeOf(SchemeFloat.class)) {
            return new SchemeFloat(this.getValue() / ((SchemeFloat) number).getValue());
        } else {
            // number is of type SchemeFraction
            return new SchemeFraction(new Fraction(this.getValue()).divide(((SchemeFraction) number).getValue()));
        }
    }

    @Override
    public String toString() {
        return String.valueOf(intVal);
    }
}
