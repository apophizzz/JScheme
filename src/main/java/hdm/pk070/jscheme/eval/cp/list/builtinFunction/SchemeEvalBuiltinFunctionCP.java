package hdm.pk070.jscheme.eval.cp.list.builtinFunction;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.ListEvaluator;
import hdm.pk070.jscheme.eval.cp.SchemeEvalCP;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
public class SchemeEvalBuiltinFunctionCP extends SchemeContinuationFunction {

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();

        SchemeBuiltinFunction builtinFunction = (SchemeBuiltinFunction) arguments[0];
        SchemeObject argumentList = (SchemeObject) arguments[1];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[2];

        int argCount = 0;
        if (arguments.length == 4) {
            argCount = (int) arguments[3];
        }


        if (!argumentList.typeOf(SchemeNil.class)) {
            argCount++;
            SchemeObject currentArg = ((SchemeCons) argumentList).getCar();

            continuation.setArguments(builtinFunction, ((SchemeCons) argumentList).getCdr(), environment, argCount);
            continuation.setProgramCounter(new SchemeEvalBuiltinFunctionCP2());
            return SchemeContinuation.create(continuation, SchemeEvalCP.getInstance(), currentArg, environment);
        }

        // call built-in function and set result at caller continuation
        continuation.getCallerContinuation().setReturnValue(builtinFunction.call(argCount));
        return continuation.getCallerContinuation();
    }
}
