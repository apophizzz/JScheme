package hdm.pk070.jscheme.obj;

import java.util.Objects;

/**
 * @author patrick.kleindienst
 */
public abstract class SchemeObject {


    protected SchemeObject() {

    }


    public final <T extends SchemeObject> boolean typeOf(Class<T> clazz) {
        if (Objects.nonNull(clazz)) {
            return this.getClass().equals(clazz);
        }
        throw new IllegalArgumentException("Parameter 'clazz' must not be null.");
    }

    public abstract Object getValue();

    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        } else if (!(obj instanceof SchemeObject)) {
            return false;
        } else if (obj.getClass() != this.getClass()) {
            return false;
        } else {
            return ((SchemeObject) obj).getValue().equals(this.getValue());
        }
    }
}
