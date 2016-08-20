package hdm.pk070.jscheme.eval.cp.list.customFunction;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.cp.SchemeEvalCP;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunction;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.LocalEnvironment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

import java.util.Objects;

/**
 * @author patrick.kleindienst
 */
@SuppressWarnings("unchecked")
public class SchemeEvalCustomUserFunctionCP extends SchemeContinuationFunction {


    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();

        SchemeCustomUserFunction customUserFunction = (SchemeCustomUserFunction) arguments[0];
        SchemeObject argumentList = (SchemeObject) arguments[1];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[2];

        LocalEnvironment functionBodyEvalEnvironment;
        if (arguments.length > 3 && Objects.nonNull(arguments[3])) {
            functionBodyEvalEnvironment = (LocalEnvironment) arguments[3];
        } else {
            functionBodyEvalEnvironment = LocalEnvironment.withSizeAndParent
                    (customUserFunction.getRequiredSlotsCount(), customUserFunction.getHomeEnvironment());
        }

        SchemeObject functionParameterList;
        if (arguments.length > 4 && Objects.nonNull(arguments[4])) {
            functionParameterList = (SchemeObject) arguments[4];
        } else {
            functionParameterList = customUserFunction.getParameterList();
        }

        int argumentCount = 0;
        if (arguments.length > 5) {
            argumentCount = (int) arguments[5];
        }

        if (functionParameterList.typeOf(SchemeCons.class)) {
            // If we have another parameter, but there's no matching argument -> throw SchemeError
            if (!argumentList.typeOf(SchemeCons.class)) {
                throw new SchemeError(String.format("(eval): arity mismatch, expected number of " +
                        "arguments does not match the given number [expected: %d, given: %d]", customUserFunction
                        .getParamCount(), argumentCount));
            }

            argumentCount++;

            SchemeObject unevaluatedArgument = ((SchemeCons) argumentList).getCar();

            continuation.setArguments(customUserFunction, argumentList, environment,
                    functionBodyEvalEnvironment, functionParameterList, argumentCount);
            continuation.setProgramCounter(new SchemeEvalCustomUserFunctionCP2());
            return SchemeContinuation.create(continuation, SchemeEvalCP.getInstance(), unevaluatedArgument,
                    environment);

        }

        if (!argumentList.typeOf(SchemeNil.class)) {
            throw new SchemeError(String.format("(eval): arity mismatch, expected number of " +
                            "arguments does not match the given number [expected: %d, more given!]",
                    customUserFunction.getParamCount()));
        }


        SchemeObject bodyList = customUserFunction.getFunctionBodyList();

        continuation.setProgramCounter(new SchemeEvalCustomUserFunctionCP3());
        continuation.setArguments(bodyList, functionBodyEvalEnvironment);
        return continuation;
    }
}
