package hdm.pk070.jscheme.obj.builtin.syntax.cp.define_cp;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeVoid;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
@SuppressWarnings("unchecked")
public class SchemeBuiltinSyntaxDefineCP_VariableBinding2 extends SchemeContinuationFunction {

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        SchemeSymbol variableName = (SchemeSymbol) arguments[0];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[1];

        SchemeObject evaluatedVariableValue = continuation.getReturnValue();
        environment.add(EnvironmentEntry.create(variableName, evaluatedVariableValue));

        continuation.getCallerContinuation().setReturnValue(new SchemeVoid());
        return continuation.getCallerContinuation();
    }
}
