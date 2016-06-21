package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeInteger;
import hdm.pk070.jscheme.stack.SchemeCallStack;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author patrick.kleindienst
 */
public class SchemeBuiltinMinus extends SchemeBuiltinFunction {

    public static SchemeBuiltinMinus create() {
        return new SchemeBuiltinMinus("-");
    }

    private SchemeBuiltinMinus(String internalName) {
        super(internalName);
    }

    @Override
    public SchemeObject call(int argCount) throws SchemeError {
        int difference;

        // throw SchemeError if argCount == 0
        if (argCount == 0) {
            throw new SchemeError("(-): expected number of arguments does not match the given number [expected: 1 or " +
                    "more, given: 0]");
        }

        // if argCount == 1, return inverse of single argument
        else if (argCount == 1) {
            SchemeObject singleArg = SchemeCallStack.instance().pop();
            // throw SchemeError if popped arg is not a number
            if (!singleArg.typeOf(SchemeInteger.class)) {
                throw new SchemeError(String.format("(-): contract violation [expected: number, given: %s]",
                        singleArg));
            }
            // if the single arg is a number, return inverse
            return new SchemeInteger(((SchemeInteger) singleArg).getValue() * -1);
        }

        // in all the other cases: argCount is valid
        else {

            // collect argCount pushed arguments in a list (pop from stack)
            List<SchemeObject> argsList = new LinkedList<>();
            for (int i = 0; i < argCount; i++) {
                SchemeObject nextArg = SchemeCallStack.instance().pop();
                if (!nextArg.typeOf(SchemeInteger.class)) {
                    throw new SchemeError(String.format("(-): contract violation [expected: number, given: %s]",
                            nextArg));
                }
                argsList.add(nextArg);
            }

            // reverse list, since we need the arguments in reversed order for subtraction (args have been pushed
            // from left to right!)
            Collections.reverse(argsList);
            // Start with the minuend (first argument that has been pushed and last which has been popped, now at
            // beginning of reversed list)
            difference = ((SchemeInteger) argsList.remove(0)).getValue();

            // apply the subtrahends on the minuend
            for (SchemeObject schemeObject : argsList) {
                difference -= ((SchemeInteger) schemeObject).getValue();
            }
        }
        return new SchemeInteger(difference);
    }

    @Override
    public String toString() {
        return "<procedure:->";
    }
}
