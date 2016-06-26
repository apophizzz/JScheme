package hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex;

/**
 * @author patrick.kleindienst
 */
public final class SchemeFloat extends SchemeFloatComplex {

    private float floatVal;

    public SchemeFloat(float floatVal) {
        this.floatVal = floatVal;
    }

    @Override
    public Object getValue() {
        return floatVal;
    }

    @Override
    public String toString() {
        return String.valueOf(floatVal);
    }
}
