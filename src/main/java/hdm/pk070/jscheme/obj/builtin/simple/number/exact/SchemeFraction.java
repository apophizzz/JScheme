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

    public SchemeFraction(double numerator) {
        this.fraction = new Fraction(numerator);
    }

    public SchemeFraction(double numerator, double denominator) {
        this.fraction = new Fraction(numerator, denominator, 100);
    }

    public SchemeFraction(double numerator, int denominator) {
        this.fraction = new Fraction(numerator, denominator);
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
        return null;
    }

    @Override
    public SchemeNumber multiply(SchemeNumber number) {
        return null;
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
