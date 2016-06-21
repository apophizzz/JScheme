package hdm.pk070.jscheme.obj.function.builtin;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.simple.SchemeInteger;
import hdm.pk070.jscheme.stack.SchemeCallStack;

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
        int difference = 0;

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
            // get value at stack index 0
            SchemeObject firstArg = SchemeCallStack.instance().firstElement();
            // first arg must be a number, throw SchemeError if not
            if (!firstArg.typeOf(SchemeInteger.class)) {
                throw new SchemeError(String.format("(-): contract violation [expected: number, given: %s]",
                        firstArg));
            }

            // start with first argument (stack index 0 -> bottom)
            difference = ((SchemeInteger) firstArg).getValue();
            SchemeCallStack.instance().removeLowermost();

            while (!SchemeCallStack.instance().isEmpty()) {
                SchemeObject nextArg = SchemeCallStack.instance().firstElement();
                // check argument type
                if (!nextArg.typeOf(SchemeInteger.class)) {
                    throw new SchemeError(String.format("(-): contract violation [expected: number, given: %s]",
                            nextArg));
                }
                // compute new difference
                difference -= ((SchemeInteger) nextArg).getValue();
                // remove lowermost arg (bottom of stack)
                SchemeCallStack.instance().removeLowermost();
            }
        }

        return new SchemeInteger(difference);
    }
}
