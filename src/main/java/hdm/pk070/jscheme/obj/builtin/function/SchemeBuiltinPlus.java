package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * Implementing "+" as a built-in function for JScheme
 *
 * @author patrick.kleindienst
 */
public class SchemeBuiltinPlus extends SchemeBuiltinFunction {


    public static SchemeBuiltinPlus create() {
        return new SchemeBuiltinPlus("+");
    }

    private SchemeBuiltinPlus(String internalName) {
        super(internalName);
    }

    @Override
    public SchemeNumber call(int argCount) throws SchemeError {
        SchemeNumber result = new SchemeInteger(0);

        for (int i = 0; i < argCount; i++) {
            SchemeObject poppedArg = SchemeCallStack.instance().pop();
            if (!poppedArg.subtypeOf(SchemeNumber.class)) {
                throw new SchemeError(String.format("(+): contract violation [expected: number, given: %s]",
                        poppedArg));
            }
            result = result.add(((SchemeNumber) poppedArg));
        }
        return result;
    }
}
