package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is an abstract class defining a template for concrete evaluator classes.
 *
 * @author patrick.kleindienst
 */
abstract class AbstractEvaluator<T extends SchemeObject> {

    protected static final Logger LOGGER = LogManager.getLogger("hdm.pk070.jscheme.EvalLogger");

    /**
     * Evaluate an expression in the context of a certain {@link Environment}. Depending on the type
     * of {@code expression} the corresponding {@link AbstractEvaluator} implementation will be applied.
     *
     * @param expression
     *         The expression that shall be evaluated.
     * @param environment
     *         The environment within which {@code expression} shall be evaluated.
     * @return The result of evaluating {@code expression} in the context of {@code environment}.
     * @throws SchemeError
     *         In case anything goes wrong during evaluation.
     */
    public abstract SchemeObject doEval(T expression, Environment<SchemeSymbol, EnvironmentEntry> environment) throws
            SchemeError;
}
