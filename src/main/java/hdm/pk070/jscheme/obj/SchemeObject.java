package hdm.pk070.jscheme.obj;

import hdm.pk070.jscheme.obj.tag.Tag;

import java.util.Objects;

/**
 * Created by patrick on 19.04.16.
 */
public abstract class SchemeObject {


    private Tag tag;

    protected SchemeObject(Tag tag) {
        Objects.requireNonNull(tag);
        this.tag = tag;
    }


    public Tag getTag() {
        return tag;
    }

    public <T extends SchemeObject> boolean typeOf(Class<T> clazz) {
        if (Objects.nonNull(clazz)) {
            return this.getClass().equals(clazz);
        }
        throw new IllegalArgumentException("Parameter 'clazz' must not be null.");
    }

}
