package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeFraction;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.stack.SchemeCallStack;

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
    public SchemeNumber call(int argCount) throws SchemeError {
        int quotient;

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

            return new SchemeFraction(1, ((SchemeInteger) poppedArg).getValue());
        }

        return null;
    }

    @Override
    public String toString() {
        return "<procedure:/>";
    }
}
