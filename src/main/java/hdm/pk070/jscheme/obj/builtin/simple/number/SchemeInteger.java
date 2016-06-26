package hdm.pk070.jscheme.obj.builtin.simple.number;

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
}
