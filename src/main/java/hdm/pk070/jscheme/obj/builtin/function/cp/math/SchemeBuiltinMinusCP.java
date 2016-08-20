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
 * @author patrick.kleindienst
 */
public class SchemeBuiltinMinusCP extends SchemeBuiltinFunctionCP {

    public static SchemeBuiltinMinusCP create() {
        return new SchemeBuiltinMinusCP();
    }

    private SchemeBuiltinMinusCP() {
        super("-");
    }

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        int argCount = (int) arguments[0];

        SchemeNumber difference;

        // throw SchemeError if argCount == 0
        if (argCount == 0) {
            throw new SchemeError("(-): expected number of arguments does not match the given number [expected: 1 or " +
                    "more, given: 0]");
        }

        // if argCount == 1, return inverse of single argument
        else if (argCount == 1) {
            SchemeObject singleArg = SchemeCallStack.instance().pop();
            // throw SchemeError if popped arg is not a number
            if (!singleArg.subtypeOf(SchemeNumber.class)) {
                throw new SchemeError(String.format("(-): contract violation [expected: number, given: %s]",
                        singleArg));
            }
            // if the single arg is a number, return inverse
            continuation.getCallerContinuation().setReturnValue(((SchemeNumber) singleArg).multiply(new SchemeInteger
                    (-1)));
            return continuation.getCallerContinuation();
        }

        // in all the other cases: argCount is valid
        else {

            // collect argCount pushed arguments in a list (pop from stack)
            List<SchemeNumber> argsList = new LinkedList<>();
            for (int i = 0; i < argCount; i++) {
                SchemeObject nextArg = SchemeCallStack.instance().pop();
                if (!nextArg.subtypeOf(SchemeNumber.class)) {
                    throw new SchemeError(String.format("(-): contract violation [expected: number, given: %s]",
                            nextArg));
                }
                argsList.add(((SchemeNumber) nextArg));
            }

            // reverse list, since we need the arguments in reversed order for subtraction (args have been pushed
            // from left to right!)
            Collections.reverse(argsList);
            // Start with the minuend (first argument that has been pushed and last which has been popped, now at
            // beginning of reversed list)
            difference = argsList.remove(0);

            // apply the subtrahends on the minuend
            for (SchemeNumber schemeNumber : argsList) {
                difference = difference.subtract(schemeNumber);
            }
        }
        continuation.getCallerContinuation().setReturnValue(difference);
        return continuation.getCallerContinuation();
    }
}
