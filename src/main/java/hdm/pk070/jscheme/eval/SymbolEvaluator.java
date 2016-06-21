package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import hdm.pk070.jscheme.reader.SchemeReader;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.GlobalEnvironment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

import java.util.Optional;

/**
 * This evaluator is called in case the {@link SchemeReader} returned a {@link SchemeSymbol}
 * as an intermediate result. The {@link SymbolEvaluator}'s job is to lookup the symbol in
 * {@link GlobalEnvironment} and return it or throw an error in case it does not exist.
 *
 * @author patrick.kleindienst
 */
class SymbolEvaluator extends AbstractEvaluator<SchemeSymbol> {

    static SymbolEvaluator getInstance() {
        return new SymbolEvaluator();
    }

    private SymbolEvaluator() {
    }

    @Override
    public SchemeObject doEval(SchemeSymbol expression, Environment<SchemeSymbol, EnvironmentEntry> environment)
            throws SchemeError {
        // Get symbol from global environment
        LOGGER.debug(String.format("Evaluating symbol %s ...", expression.getValue()));
        Optional<EnvironmentEntry> entryOptional = environment.get(expression);
        // Throw SchemeError if not present in environment
        if (!entryOptional.isPresent()) {
            LOGGER.debug(String.format("Unable to find symbol %s in environment. Throwing SchemeError.",
                    expression.getValue()));
            throw new SchemeError(String.format("undefined variable %s", expression.getValue()));
        }
        // Return existing value
        LOGGER.debug(String.format("Evaluation of symbol %s yielded: " + entryOptional.get(), expression.getValue()));
        return entryOptional.get().getValue();
    }
}
