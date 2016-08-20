package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeFunction;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;

/**
 * @author patrick.kleindienst
 */
public abstract class SchemeBuiltinFunctionCP extends SchemeFunction {

    protected SchemeBuiltinFunctionCP(final String internalName) {
        super(internalName);
    }

    public abstract SchemeContinuation call(SchemeContinuation continuation) throws SchemeError;
}
