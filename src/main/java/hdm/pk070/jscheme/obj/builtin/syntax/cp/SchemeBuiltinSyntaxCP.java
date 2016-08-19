package hdm.pk070.jscheme.obj.builtin.syntax.cp;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;

/**
 * @author patrick.kleindienst
 */
public abstract class SchemeBuiltinSyntaxCP extends SchemeObject {

    private final String internalName;

    protected SchemeBuiltinSyntaxCP(String internalName) {
        this.internalName = internalName;
    }

    public abstract SchemeContinuation apply(SchemeContinuation continuation) throws SchemeError;

    @Override
    public Object getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "<syntax:" + this.internalName + ">";
    }
}
