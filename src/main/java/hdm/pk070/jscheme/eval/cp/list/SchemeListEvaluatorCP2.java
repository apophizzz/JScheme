package hdm.pk070.jscheme.eval.cp.list;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.cp.list.builtinFunction.SchemeEvalBuiltinFunctionCP;
import hdm.pk070.jscheme.eval.cp.list.builtinSyntax.SchemeEvalBuiltinSyntaxCP;
import hdm.pk070.jscheme.eval.cp.list.customFunction.SchemeEvalCustomUserFunctionCP;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.syntax.cp.SchemeBuiltinSyntaxCP;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunction;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
@SuppressWarnings("unchecked")
public class SchemeListEvaluatorCP2 extends SchemeContinuationFunction {

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        SchemeCons expression = (SchemeCons) arguments[0];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[1];

        SchemeObject evaluatedFunctionSlot = continuation.getReturnValue();
        SchemeObject argumentList = expression.getCdr();

        if (evaluatedFunctionSlot.subtypeOf(SchemeBuiltinFunction.class)) {
            continuation.setArguments(evaluatedFunctionSlot, argumentList, environment);
            continuation.setProgramCounter(new SchemeEvalBuiltinFunctionCP());
            return continuation;
        } else if (evaluatedFunctionSlot.subtypeOf(SchemeBuiltinSyntaxCP.class)) {
            continuation.setArguments(evaluatedFunctionSlot, argumentList, environment);
            continuation.setProgramCounter(new SchemeEvalBuiltinSyntaxCP());
            return continuation;
        } else if (evaluatedFunctionSlot.typeOf(SchemeCustomUserFunction.class)) {
            continuation.setArguments(evaluatedFunctionSlot, argumentList, environment);
            continuation.setProgramCounter(new SchemeEvalCustomUserFunctionCP());
            return continuation;
        }

        // Reaching this section means we don't have a valid function slot -> throw SchemeError
        throw new SchemeError(String.format("application: not a procedure [expected: procedure that can be applied to" +
                " arguments, given: %s]", evaluatedFunctionSlot));
    }
}
