package hdm.pk070.jscheme.eval.cp.symbol;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

import java.util.Optional;

/**
 * Evaluate a {@link SchemeSymbol} in CP-style.
 *
 * @author patrick.kleindienst
 */
@SuppressWarnings("unchecked")
public class SchemeSymbolEvaluatorCP extends SchemeContinuationFunction {

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        SchemeSymbol expression = (SchemeSymbol) arguments[0];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[1];

        // Get symbol from global environment
        Optional<EnvironmentEntry> entryOptional = environment.get(expression);
        // Throw SchemeError if not present in environment
        if (!entryOptional.isPresent()) {
            throw new SchemeError(String.format("undefined variable %s", expression.getValue()));
        }
        // Return existing value
        continuation.getCallerContinuation().setReturnValue(entryOptional.get().getValue());
        return continuation.getCallerContinuation();
    }
}
