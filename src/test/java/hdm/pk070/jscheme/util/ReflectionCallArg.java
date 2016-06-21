package hdm.pk070.jscheme.util;

import java.util.Objects;

/**
 * @author patrick.kleindienst
 */
public class ReflectionCallArg {

    private Class clazz;
    private Object value;

    public ReflectionCallArg(Class clazz, Object value) {
        Objects.requireNonNull(clazz);
        this.clazz = clazz;
        this.value = value;
    }

    public Class getClazz() {
        return clazz;
    }

    public Object getValue() {
        return value;
    }
}
