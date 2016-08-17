package hdm.pk070.jscheme.obj;

/**
 * This class acts as a base class for all types of JScheme functions.
 *
 * @author patrick.kleindienst
 */
public class SchemeFunction extends SchemeObject {

    private final String internalName;

    protected SchemeFunction(String internalName) {
        this.internalName = internalName;
    }

    public String getInternalName() {
        return this.internalName;
    }

    @Override
    public Object getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "<procedure:" + this.getInternalName() + ">";
    }
}
