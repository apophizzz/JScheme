package hdm.pk070.jscheme.obj.builtin.function.cp.math;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunctionCP;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * Built-in "abs" in CP-style.
 *
 * @author patrick.kleindienst
 */
public class SchemeBuiltinAbsoluteCP extends SchemeBuiltinFunctionCP {


    public static SchemeBuiltinAbsoluteCP create() {
        return new SchemeBuiltinAbsoluteCP();
    }

    private SchemeBuiltinAbsoluteCP() {
        super("abs");
    }

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        int argCount = (int) arguments[0];

        if (argCount != 1) {
            throw new SchemeError(String.format("(abs): arity mismatch, expected number of arguments does not match " +
                    "given number [expected: 1, given: %d]", argCount));
        } else {
            SchemeObject poppedArg = SchemeCallStack.instance().pop();
            if (!poppedArg.subtypeOf(SchemeNumber.class)) {
                throw new SchemeError(String.format("(abs): contract violation [expected: number, given: %s]",
                        poppedArg));
            } else {
                continuation.getCallerContinuation().setReturnValue(((SchemeNumber) poppedArg).absolute());
                return continuation.getCallerContinuation();
            }
        }
    }
}
