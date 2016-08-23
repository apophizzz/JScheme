package hdm.pk070.jscheme.obj.builtin.function.cp.base;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunctionCP;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeFalse;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeTrue;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * Built-in "eq?" in CP-style.
 *
 * @author patrick.kleindienst
 */
public class SchemeBuiltinEqCP extends SchemeBuiltinFunctionCP {

    public static SchemeBuiltinEqCP create() {
        return new SchemeBuiltinEqCP();
    }

    private SchemeBuiltinEqCP() {
        super("eq?");
    }

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        int argCount = (int) arguments[0];

        if (argCount != 2) {
            throw new SchemeError(String.format("(eq?): arity mismatch, expected number of arguments does not match " +
                    "the given" + " number [expected: 2, given: %d]", argCount));
        }

        SchemeObject argument2 = SchemeCallStack.instance().pop();
        SchemeObject argument1 = SchemeCallStack.instance().pop();

        if (argument2 == argument1) {
            continuation.getCallerContinuation().setReturnValue(new SchemeTrue());
        } else if (!argument2.typeOf(SchemeCons.class) && argument2.equals(argument1)) {
            continuation.getCallerContinuation().setReturnValue(new SchemeTrue());
        } else {
            continuation.getCallerContinuation().setReturnValue(new SchemeFalse());
        }

        return continuation.getCallerContinuation();
    }
}
