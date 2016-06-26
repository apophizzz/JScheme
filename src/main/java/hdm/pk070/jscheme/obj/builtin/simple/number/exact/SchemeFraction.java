package hdm.pk070.jscheme.obj.builtin.simple.number.exact;

import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeExactNumber;
import org.apache.commons.math3.fraction.Fraction;

/**
 * Created by patrick on 24.06.16.
 */
public final class SchemeFraction extends SchemeExactNumber {

    private Fraction fraction;

    public SchemeFraction(int numerator, int denominator) {
        this.fraction = new Fraction(numerator, denominator);
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
        return null;
    }

    @Override
    public SchemeNumber subtract(SchemeNumber number) {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public String toString() {
        return fraction.toString().replace(" ", "");
    }
}
