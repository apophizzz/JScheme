package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeInteger;
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
    public SchemeObject call(int argCount) throws SchemeError {
        int sum = 0;
        // pop argCount values from stack
        for (int i = 0; i < argCount; i++) {
            SchemeObject poppedArg = SchemeCallStack.instance().pop();
            // each arg must be of type SchemeInteger
            if (!poppedArg.typeOf(SchemeInteger.class)) {
                throw new SchemeError(String.format("(+): non-integer argument %s!", poppedArg.toString()));
            }
            sum += ((SchemeInteger) poppedArg).getValue();
        }
        return new SchemeInteger(sum);
    }

    @Override
    public String toString() {
        return "<procedure:+>";
    }
}
