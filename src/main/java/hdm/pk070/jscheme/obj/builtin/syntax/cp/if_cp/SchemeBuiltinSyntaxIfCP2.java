package hdm.pk070.jscheme.obj.builtin.syntax.cp.if_cp;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.cp.SchemeEvalCP;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeTrue;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
public class SchemeBuiltinSyntaxIfCP2 extends SchemeContinuationFunction {


    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        SchemeObject conditionMetExpr = (SchemeObject) arguments[0];
        SchemeObject elseExpr = (SchemeObject) arguments[1];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[2];

        SchemeObject evaluatedConditionExpr = continuation.getReturnValue();
        if (isNonZeroNumber(evaluatedConditionExpr) || isNonEmptyString(evaluatedConditionExpr) || isSchemeTrue
                (evaluatedConditionExpr)) {
            continuation.setArguments(conditionMetExpr, environment);
        } else {
            continuation.setArguments(elseExpr, environment);
        }
        continuation.setProgramCounter(SchemeEvalCP.getInstance());
        return continuation;
    }


    private boolean isNonZeroNumber(SchemeObject conditionValue) {
        return conditionValue.subtypeOf(SchemeNumber.class) && (((SchemeNumber) conditionValue).getValue()
                .intValue() != 0);
    }

    private boolean isNonEmptyString(SchemeObject conditionValue) {
        return conditionValue.typeOf(SchemeString.class) && !((SchemeString) conditionValue).getValue().equals("");
    }

    private boolean isSchemeTrue(SchemeObject conditionValue) {
        return conditionValue.typeOf(SchemeTrue.class);
    }

}