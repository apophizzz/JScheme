package hdm.pk070.jscheme.obj.builtin.function.cp.math;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunctionCP;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * @author patrick.kleindienst
 */
public class SchemeBuiltinTimesCP extends SchemeBuiltinFunctionCP {

    public static SchemeBuiltinTimesCP create() {
        return new SchemeBuiltinTimesCP();
    }

    private SchemeBuiltinTimesCP() {
        super("*");
    }

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        int argCount = (int) arguments[0];

        SchemeNumber product = new SchemeInteger(1);

        // pop argCount arguments from stack and multiply them
        for (int i = 0; i < argCount; i++) {
            SchemeObject currentArg = SchemeCallStack.instance().pop();
            // check if currentArg is of type SchemeInteger
            if (!currentArg.subtypeOf(SchemeNumber.class)) {
                throw new SchemeError(String.format("(*): contract violation [expected: number, given: %s]",
                        currentArg));
            }
            // do multiply
            product = product.multiply(((SchemeNumber) currentArg));
        }

        continuation.getCallerContinuation().setReturnValue(product);
        return continuation.getCallerContinuation();
    }
}
