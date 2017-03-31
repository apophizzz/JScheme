package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.reader.SchemeReader;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * This class constitutes the JScheme entry point for evaluating the {@link SchemeObject}s returned by
 * {@link SchemeReader}. It checks the type of the incoming expression and invokes the
 * corresponding {@link AbstractEvaluator} implementation or simply returns the object in case it evaluates to itself.
 *
 * @author patrick.kleindienst
 */
public class SchemeEval {

    public static SchemeEval getInstance() {
        return new SchemeEval();
    }

    private SchemeEval() {
    }


    /**
     * Gets passed an expression by the {@link SchemeReader} and hands it on to a specialized evaluation class
     * according to the expression type.
     *
     * @param expression
     *         The expression to evaluate as passed by {@link SchemeReader}.
     * @param environment
     *         The evaluation context.
     * @return A {@link SchemeObject} as evaluation result.
     * @throws SchemeError
     *         If evaluation fails for some reason.
     */
    public SchemeObject eval(SchemeObject expression, Environment<SchemeSymbol, EnvironmentEntry> environment) throws
            SchemeError {
        if (expression.typeOf(SchemeSymbol.class)) {
            return SymbolEvaluator.getInstance().doEval(((SchemeSymbol) expression), environment);
        } else if (expression.typeOf(SchemeCons.class)) {
            return ListEvaluator.getInstance().doEval(((SchemeCons) expression), environment);
        } else {
            // Return expression without further evaluation in all the other cases.
            return expression;
        }
    }
}
