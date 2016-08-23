package hdm.pk070.jscheme.obj.builtin.function.cp.math;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunctionCP;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.stack.SchemeCallStack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Built-in "/" in CP-style.
 *
 * @author patrick.kleindienst
 */
public class SchemeBuiltinDivideCP extends SchemeBuiltinFunctionCP {


    public static SchemeBuiltinDivideCP create() {
        return new SchemeBuiltinDivideCP();
    }

    private SchemeBuiltinDivideCP() {
        super("/");
    }

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        int argCount = (int) arguments[0];

        SchemeNumber divisionResult;

        // Division expects at least one argument
        if (argCount == 0) {
            throw new SchemeError("(/): arity mismatch, expected number of arguments does not match given number " +
                    "[expected: at least 1, given 0]");
        } else if (argCount == 1) {
            SchemeObject poppedArg = SchemeCallStack.instance().pop();

            // Check if popped arg is number
            if (!poppedArg.subtypeOf(SchemeNumber.class)) {
                throw new SchemeError(String.format("(/): contract violation [expected: number, given: %s]",
                        poppedArg));
            }
            continuation.getCallerContinuation().setReturnValue(new SchemeInteger(1).divide(((SchemeNumber)
                    poppedArg)));
            return continuation.getCallerContinuation();
        } else {

            List<SchemeNumber> argList = new LinkedList<>();
            for (int i = 0; i < argCount; i++) {
                SchemeObject poppedArg = SchemeCallStack.instance().pop();
                if (!poppedArg.subtypeOf(SchemeNumber.class)) {
                    throw new SchemeError(String.format("(/): contract violation [expected: number, given: %s]",
                            poppedArg));
                }
                argList.add((SchemeNumber) poppedArg);
            }

            // Invert order of arguments from stack since we need them in reverse order for division
            Collections.reverse(argList);

            // Start with first argument
            divisionResult = argList.remove(0);

            for (SchemeNumber currentArg : argList) {
                divisionResult = divisionResult.divide(currentArg);
            }
            continuation.getCallerContinuation().setReturnValue(divisionResult);
            return continuation.getCallerContinuation();
        }
    }
}
