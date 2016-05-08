package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.table.environment.GlobalEnvironment;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is an abstract class defining a template for concrete evaluator classes.
 *
 * @author patrick.kleindienst
 */
abstract class AbstractEvaluator {

    protected static final Logger LOGGER = LogManager.getLogger("hdm.pk070.jscheme.EvalLogger");

    protected static final GlobalEnvironment GLOBAL_ENVIRONMENT = GlobalEnvironment.getInstance();

    public abstract SchemeObject doEval(SchemeSymbol expression) throws SchemeError;
}
