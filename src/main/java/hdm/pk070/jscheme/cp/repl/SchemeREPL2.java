package hdm.pk070.jscheme.cp.repl;

import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.print.SchemePrint;

/**
 * REPL part 2: Printing the result and re-entering the REPL.
 *
 * @author patrick.kleindienst
 */
public class SchemeREPL2 extends SchemeContinuationFunction {


    @Override
    public SchemeContinuation call(SchemeContinuation continuation) {
        SchemeObject returnValue = continuation.getReturnValue();
        SchemePrint.printEvalResult(returnValue);

        continuation.setProgramCounter(new SchemeREPL());

        return continuation;
    }
}
