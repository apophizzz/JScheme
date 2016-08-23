package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeFunction;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;

/**
 * The abstract representation of a built-in function in CP-style.
 *
 * @author patrick.kleindienst
 */
public abstract class SchemeBuiltinFunctionCP extends SchemeFunction {

    protected SchemeBuiltinFunctionCP(final String internalName) {
        super(internalName);
    }

    /**
     * Calling a built-in function.
     *
     * @param continuation
     *         The {@link SchemeContinuation} (i.e. stack frame) for the function call.
     * @return The caller continuation along with the result.
     * @throws SchemeError
     *         If anything goes wrong during function call.
     */
    public abstract SchemeContinuation call(SchemeContinuation continuation) throws SchemeError;
}
