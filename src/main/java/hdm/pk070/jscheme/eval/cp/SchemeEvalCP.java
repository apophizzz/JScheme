package hdm.pk070.jscheme.eval.cp;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.SymbolEvaluator;
import hdm.pk070.jscheme.eval.cp.list.SchemeListEvaluatorCP;
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
public class SchemeEvalCP extends SchemeContinuationFunction {

    public static SchemeEvalCP getInstance() {
        return new SchemeEvalCP();
    }

    private SchemeEvalCP() {

    }


    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();

        // Get expression from cont arguments
        SchemeObject expression = (SchemeObject) arguments[0];

        // Get environment from cont arguments
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[1];

        if (expression.typeOf(SchemeSymbol.class)) {
            SchemeObject result = SymbolEvaluator.getInstance().doEval((SchemeSymbol) expression, environment);
            continuation.getCallerContinuation().setReturnValue(result);
            return continuation.getCallerContinuation();
        } else if (expression.typeOf(SchemeCons.class)) {
            continuation.setArguments(expression, environment);
            continuation.setProgramCounter(new SchemeListEvaluatorCP());
            return continuation;
        }

        continuation.getCallerContinuation().setReturnValue(expression);

        // Return the caller's continuation -> This is where we want to proceed!
        return continuation.getCallerContinuation();
    }
}
