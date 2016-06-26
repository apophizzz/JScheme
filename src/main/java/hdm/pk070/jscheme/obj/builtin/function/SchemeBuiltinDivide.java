package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * Created by patrick on 24.06.16.
 */
public final class SchemeBuiltinDivide extends SchemeBuiltinFunction {


    public static SchemeBuiltinDivide create() {
        return new SchemeBuiltinDivide("/");
    }

    private SchemeBuiltinDivide(String internalName) {
        super(internalName);
    }

    @Override
    public SchemeObject call(int argCount) throws SchemeError {
        int quotient;

        if (argCount == 0) {
            throw new SchemeError("(/): arity mismatch, expected number of arguments does not match given number " +
                    "[expected: at least 1, given 0]");
        }

        return null;
    }
}
