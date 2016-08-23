package hdm.pk070.jscheme.cp;

import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;

/**
 * The finish function defines the program counter for the starting continuation.
 *
 * @author patrick.kleindienst
 */
public class SchemeFinish extends SchemeContinuationFunction {

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) {
        System.exit(0);
        return null;
    }
}
