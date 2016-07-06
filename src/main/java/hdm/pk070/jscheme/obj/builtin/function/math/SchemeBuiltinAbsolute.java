package hdm.pk070.jscheme.obj.builtin.function.math;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * @author patrick.kleindienst
 */
public final class SchemeBuiltinAbsolute extends SchemeBuiltinFunction {

    public static SchemeBuiltinAbsolute create() {
        return new SchemeBuiltinAbsolute("abs");
    }

    private SchemeBuiltinAbsolute(String internalName) {
        super(internalName);
    }

    @Override
    public SchemeObject call(int argCount) throws SchemeError {
        if (argCount != 1) {
            throw new SchemeError(String.format("(abs): arity mismatch, expected number of arguments does not match " +
                    "given number [expected: 1, given: %d]", argCount));
        } else {
            SchemeObject poppedArg = SchemeCallStack.instance().pop();
            if (!poppedArg.subtypeOf(SchemeNumber.class)) {
                throw new SchemeError(String.format("(abs): contract violation [expected: number, given: %s]",
                        poppedArg));
            } else {
                return ((SchemeNumber) poppedArg).absolute();
            }
        }
    }
}
