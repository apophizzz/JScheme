package hdm.pk070.jscheme.obj;

import hdm.pk070.jscheme.cp.trampoline.SchemeTrampoline;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;

/**
 * This class serves as the program counter, defining a function which should be called when the
 * {@link SchemeTrampoline} is entered.
 *
 * @author patrick.kleindienst
 */
public abstract class SchemeContinuationFunction {

    /**
     * @param continuation
     *         The {@link SchemeContinuation} which can be considered the stack frame for the next function call.
     * @return The caller continuation.
     * @throws SchemeError
     *         If anything goes wrong during function call.
     */
    public abstract SchemeContinuation call(SchemeContinuation continuation) throws SchemeError;
}
