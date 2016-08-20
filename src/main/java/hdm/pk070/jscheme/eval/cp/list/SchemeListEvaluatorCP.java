package hdm.pk070.jscheme.eval.cp.list;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.cp.SchemeEvalCP;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
@SuppressWarnings("unchecked")
public class SchemeListEvaluatorCP extends SchemeContinuationFunction {

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();

        SchemeCons expression = (SchemeCons) arguments[0];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[1];

        SchemeObject functionSlot = expression.getCar();

        continuation.setProgramCounter(new SchemeListEvaluatorCP2());
        return SchemeContinuation.create(continuation, SchemeEvalCP.getInstance(), functionSlot, environment);
    }
}
