package hdm.pk070.jscheme.util;

import java.util.Objects;

/**
 * @author patrick.kleindienst
 */
public class ReflectionCallArg {

    private final Class clazz;
    private final Object value;

    public ReflectionCallArg(Class clazz, Object value) {
        Objects.requireNonNull(clazz);
        this.clazz = clazz;
        this.value = value;
    }

    public ReflectionCallArg(Object value) {
        Objects.requireNonNull(value);
        this.value = value;
        this.clazz = value.getClass();
    }

    public Class getClazz() {
        return clazz;
    }

    public Object getValue() {
        return value;
    }
}
