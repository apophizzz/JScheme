package hdm.pk070.jscheme.eval.cp.list.builtinSyntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.syntax.cp.SchemeBuiltinSyntaxCP;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
@SuppressWarnings("unchecked")
public class SchemeEvalBuiltinSyntaxCP extends SchemeContinuationFunction {

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        SchemeBuiltinSyntaxCP builtinSyntaxCP = (SchemeBuiltinSyntaxCP) arguments[0];
        SchemeObject argumentList = (SchemeObject) arguments[1];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[2];

        continuation.setArguments(argumentList, environment);

        return builtinSyntaxCP.apply(continuation);
    }
}
