package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.table.environment.GlobalEnvironment;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import hdm.pk070.jscheme.reader.SchemeReader;

import java.util.Objects;

/**
 * This evaluator is called in case the {@link SchemeReader} returned a {@link SchemeSymbol}
 * as an intermediate result. The {@link SymbolEvaluator}'s job is to lookup the symbol in
 * {@link GlobalEnvironment} and return it or throw an error in case it does not exist.
 *
 * @author patrick.kleindienst
 */
class SymbolEvaluator extends AbstractEvaluator {

    static SymbolEvaluator getInstance() {
        return new SymbolEvaluator();
    }

    private SymbolEvaluator() {
    }

    @Override
    public SchemeObject doEval(SchemeSymbol expression) throws SchemeError {
        // 1. get symbol from global environment
        LOGGER.debug(String.format("Evaluating symbol %s ...", expression.getValue()));
        SchemeObject value = GLOBAL_ENVIRONMENT.get(expression);
        // 2. throw SchemeError if not present in environment
        if (Objects.isNull(value)) {
            LOGGER.debug(String.format("Unable to find symbol %s in global environment. Throw SchemeError.",
                    expression.getValue()));
            throw new SchemeError(String.format("undefined variable %s", expression.getValue()));
        }
        // 3. return existing value
        LOGGER.debug(String.format("Evaluation of symbol %s yielded: " + value.getValue(), expression.getValue()));
        return value;
    }
}
