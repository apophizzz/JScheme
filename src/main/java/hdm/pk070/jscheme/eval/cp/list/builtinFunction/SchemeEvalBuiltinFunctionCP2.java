package hdm.pk070.jscheme.eval.cp.list.builtinFunction;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * Part 2 of built-in function evaluation. The evaluation result is pushed on the stack. Afterwards, the execution
 * flow jumps back to first part.
 *
 * @author patrick.kleindienst
 */
public class SchemeEvalBuiltinFunctionCP2 extends SchemeContinuationFunction {


    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        SchemeObject evaluatedArgument = continuation.getReturnValue();
        SchemeCallStack.instance().push(evaluatedArgument);

        continuation.setProgramCounter(new SchemeEvalBuiltinFunctionCP());
        return continuation;
    }
}
