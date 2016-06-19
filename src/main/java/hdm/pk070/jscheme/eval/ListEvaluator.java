package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeCons;
import hdm.pk070.jscheme.table.environment.Environment;

/**
 * Created by patrick on 17.06.16.
 */
public class ListEvaluator extends AbstractEvaluator<SchemeCons> {

    static ListEvaluator getInstance() {
        return new ListEvaluator();
    }

    private ListEvaluator() {
    }

    @Override
    public SchemeObject doEval(SchemeCons expression, Environment environment) throws SchemeError {
        return null;
    }
}
