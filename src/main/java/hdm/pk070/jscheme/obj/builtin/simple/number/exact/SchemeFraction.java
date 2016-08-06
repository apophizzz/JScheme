package hdm.pk070.jscheme.obj.builtin.simple.number.exact;

import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import org.apache.commons.math3.fraction.Fraction;

/**
 * Created by patrick on 24.06.16.
 */
public final class SchemeFraction extends SchemeExactNumber {

    private Fraction fraction;

    public SchemeFraction(int numerator, int denominator) {
        this.fraction = new Fraction(numerator, denominator);
    }

    public SchemeFraction(Fraction fraction) {
        this.fraction = fraction;
    }


    public SchemeFraction(int numerator) {
        this(numerator, 1);
    }


    public SchemeFloat toSchemeFloat() {
        return new SchemeFloat(this.getValue().floatValue());
    }

    @Override
    public SchemeNumber add(SchemeNumber number) {
        if (number.typeOf(SchemeFloat.class)) {
            return new SchemeFloat(this.fraction.floatValue() + ((SchemeFloat) number).getValue());
        } else if (number.typeOf(SchemeFraction.class)) {
            return new SchemeFraction(this.fraction.add(((SchemeFraction) number).getValue()));
        }
        return new SchemeFraction(this.fraction.add(((SchemeInteger) number).getValue()));
    }

    @Override
    public SchemeNumber subtract(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return new SchemeFraction(this.getValue().subtract(((SchemeInteger) number).getValue()));
        } else if (number.typeOf(SchemeFloat.class)) {
            return this.toSchemeFloat().subtract(number);
        }
        return new SchemeFraction(this.getValue().subtract(((SchemeFraction) number).getValue()));
    }

    @Override
    public SchemeNumber multiply(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return new SchemeFraction(this.getValue().multiply(((SchemeInteger) number).getValue()));
        } else if (number.typeOf(SchemeFloat.class)) {
            return this.toSchemeFloat().multiply(number);
        }
        return new SchemeFraction(this.getValue().multiply(((SchemeFraction) number).getValue()));
    }

    @Override
    public SchemeNumber divide(SchemeNumber number) {
        if (number.typeOf(SchemeInteger.class)) {
            return new SchemeFraction(this.getValue().divide(((SchemeInteger) number).getValue()));
        } else if (number.typeOf(SchemeFraction.class)) {
            return new SchemeFraction(this.getValue().divide(((SchemeFraction) number).getValue()));
        } else {
            return this.toSchemeFloat().divide(number);
        }
    }

    @Override
    public SchemeNumber absolute() {
        if (this.getValue().compareTo(new Fraction(0)) == 1) {
            return new SchemeFraction(this.getValue());
        } else {
            return this.multiply(new SchemeInteger(-1));
        }
    }

    @Override
    public Fraction getValue() {
        return this.fraction;
    }

    @Override
    public String toString() {
        return fraction.toString().replace(" ", "");
    }
}
