package hdm.pk070.jscheme.obj.builtin.function.cp.math;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunctionCP;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * Built-in "+" in CP-style.
 *
 * @author patrick.kleindienst
 */
public class SchemeBuiltinPlusCP extends SchemeBuiltinFunctionCP {

    public static SchemeBuiltinPlusCP create() {
        return new SchemeBuiltinPlusCP();
    }

    private SchemeBuiltinPlusCP() {
        super("+");
    }

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        int argCount = (int) arguments[0];

        SchemeNumber result = new SchemeInteger(0);

        for (int i = 0; i < argCount; i++) {
            SchemeObject poppedArg = SchemeCallStack.instance().pop();
            if (!poppedArg.subtypeOf(SchemeNumber.class)) {
                throw new SchemeError(String.format("(+): contract violation [expected: number, given: %s]",
                        poppedArg));
            }
            result = result.add(((SchemeNumber) poppedArg));
        }

        continuation.getCallerContinuation().setReturnValue(result);
        return continuation.getCallerContinuation();
    }
}
