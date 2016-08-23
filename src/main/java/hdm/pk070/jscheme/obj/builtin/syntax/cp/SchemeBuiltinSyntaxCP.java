package hdm.pk070.jscheme.obj.builtin.syntax.cp;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;

/**
 * Built-in syntax abstraction in CP-style.
 *
 * @author patrick.kleindienst
 */
public abstract class SchemeBuiltinSyntaxCP extends SchemeObject {

    private final String internalName;

    protected SchemeBuiltinSyntaxCP(String internalName) {
        this.internalName = internalName;
    }

    /**
     * Apply built-in syntax.
     *
     * @param continuation
     *         The {@link SchemeContinuation} (i.e. stack frame) for applying the syntax.
     * @return The caller continuation along with the result.
     * @throws SchemeError
     *         If anything goes wrong during syntax employment.
     */
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
