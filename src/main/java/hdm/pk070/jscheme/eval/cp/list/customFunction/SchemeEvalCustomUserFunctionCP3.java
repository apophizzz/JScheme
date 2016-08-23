package hdm.pk070.jscheme.eval.cp.list.customFunction;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.cp.SchemeEvalCP;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.table.environment.LocalEnvironment;

/**
 * Part 3: Evaluate the function body and return the (last) result by attaching it to the caller continuation.
 *
 * @author patrick.kleindienst
 */
public class SchemeEvalCustomUserFunctionCP3 extends SchemeContinuationFunction {

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        SchemeObject bodyList = (SchemeObject) arguments[0];
        LocalEnvironment functionBodyEvalEnv = (LocalEnvironment) arguments[1];

        if (!bodyList.typeOf(SchemeNil.class)) {
            SchemeObject nextBodyPart = ((SchemeCons) bodyList).getCar();

            continuation.setProgramCounter(this);
            continuation.setArguments(((SchemeCons) bodyList).getCdr(), functionBodyEvalEnv);
            return SchemeContinuation.create(continuation, SchemeEvalCP.getInstance(), nextBodyPart,
                    functionBodyEvalEnv);
        }
        continuation.getCallerContinuation().setReturnValue(continuation.getReturnValue());
        return continuation.getCallerContinuation();
    }
}
