package hdm.pk070.jscheme.eval.cp.list.customFunction;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunction;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.LocalEnvironment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
@SuppressWarnings("unchecked")
public class SchemeEvalCustomUserFunctionCP2 extends SchemeContinuationFunction {

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();

        SchemeCustomUserFunction customUserFunction = (SchemeCustomUserFunction) arguments[0];
        SchemeObject functionCallArgumentList = (SchemeObject) arguments[1];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[2];
        LocalEnvironment functionBodyEvalEnv = (LocalEnvironment) arguments[3];
        SchemeObject functionParameterList = (SchemeObject) arguments[4];
        int argumentCount = (int) arguments[5];


        SchemeObject evaluatedArgument = continuation.getReturnValue();
        SchemeObject currentParameter = ((SchemeCons) functionParameterList).getCar();

        functionBodyEvalEnv.add(EnvironmentEntry.create((SchemeSymbol) currentParameter,
                evaluatedArgument));

        continuation.setArguments(customUserFunction, ((SchemeCons) functionCallArgumentList).getCdr(), environment,
                functionBodyEvalEnv, ((SchemeCons) functionParameterList).getCdr(), argumentCount);
        continuation.setProgramCounter(new SchemeEvalCustomUserFunctionCP());
        return continuation;
    }
}
