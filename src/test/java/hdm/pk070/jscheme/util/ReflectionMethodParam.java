package hdm.pk070.jscheme.util;

import java.util.Objects;

/**
 * Created by patrick on 19.04.16.
 */
public class ReflectionMethodParam {

    private Class clazz;
    private Object value;

    public ReflectionMethodParam(Class clazz, Object value) {
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
