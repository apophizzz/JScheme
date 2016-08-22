package hdm.pk070.jscheme.obj;

import java.util.Objects;

/**
 * This is the most essential type in JScheme. Almost anything in JScheme is a {@link SchemeObject}.
 *
 * @author patrick.kleindienst
 */
public abstract class SchemeObject {


    protected SchemeObject() {

    }


    /**
     * Check if a {@link SchemeObject} instance has a certain type.
     *
     * @param clazz
     *         The type the object shall be checked against.
     * @return True if this object is of type <code>clazz</code>, false otherwise.
     */
    public final <T extends SchemeObject> boolean typeOf(Class<T> clazz) {
        if (Objects.nonNull(clazz)) {
            return this.getClass().equals(clazz);
        }
        throw new IllegalArgumentException("Parameter 'clazz' must not be null.");
    }

    /**
     * Check if a {@link SchemeObject} is of type <code>clazz</code> or is a subtype of it.
     *
     * @param clazz
     *         The type the object shall be checked against.
     * @return True if this object is of type <code>clazz</code> (or subtype of it), false otherwise.
     */
    public final <T extends SchemeObject> boolean subtypeOf(Class<T> clazz) {
        if (Objects.nonNull(clazz)) {
            return clazz.isAssignableFrom(this.getClass());
        }
        throw new IllegalArgumentException("Parameter 'clazz' must not be null.");
    }

    public abstract Object getValue();

    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (!(obj instanceof SchemeObject)) {
            return false;
        } else if (obj.getClass() != this.getClass()) {
            return false;
        } else {
            return ((SchemeObject) obj).getValue().equals(this.getValue());
        }
    }

    @Override
    public int hashCode() {
        return this.getValue().hashCode();
    }
}
