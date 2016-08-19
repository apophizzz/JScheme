package hdm.pk070.jscheme.obj.builtin.syntax.cp.define_cp;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.cp.SchemeEvalCP;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
public class SchemeBuiltinSyntaxDefineCP_VariableBinding extends SchemeContinuationFunction {


    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        SchemeSymbol variableName = (SchemeSymbol) arguments[0];
        SchemeCons valueCons = (SchemeCons) arguments[1];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[2];

        if (!valueCons.getCdr().typeOf(SchemeNil.class)) {
            // throw SchemeError if cdr of argument list is followed by anything else than nil
            throw new SchemeError("(define): bad syntax (multiple expressions after identifier)");
        }

        SchemeObject variableValue = valueCons.getCar();
        continuation.setProgramCounter(new SchemeBuiltinSyntaxDefineCP_VariableBinding2());
        continuation.setArguments(variableName, environment);

        return SchemeContinuation.create(continuation, SchemeEvalCP.getInstance(), variableValue, environment);
    }
}
