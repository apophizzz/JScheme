package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
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

    public abstract SchemeObject doEval(T expression, Environment<SchemeSymbol, EnvironmentEntry> environment) throws
            SchemeError;
}
