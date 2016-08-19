package hdm.pk070.jscheme.obj;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;

/**
 * @author patrick.kleindienst
 */
public abstract class SchemeContinuationFunction {

    public abstract SchemeContinuation call(SchemeContinuation continuation) throws SchemeError;
}
