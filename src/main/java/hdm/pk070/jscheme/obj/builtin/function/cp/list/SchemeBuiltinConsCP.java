package hdm.pk070.jscheme.obj.builtin.function.cp.list;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunctionCP;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * @author patrick.kleindienst
 */
public class SchemeBuiltinConsCP extends SchemeBuiltinFunctionCP {

    public static SchemeBuiltinConsCP create() {
        return new SchemeBuiltinConsCP();
    }

    private SchemeBuiltinConsCP() {
        super("cons");
    }

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        int argCount = (int) arguments[0];

        if (argCount != 2) {
            throw new SchemeError(String.format("(cons): arity mismatch, expected number of arguments does not match " +
                    "given number [expected: 2, given: %d]", argCount));
        }

        // switch order of argument
        SchemeObject arg2 = SchemeCallStack.instance().pop();
        SchemeObject arg1 = SchemeCallStack.instance().pop();

        continuation.getCallerContinuation().setReturnValue(new SchemeCons(arg1, arg2));
        return continuation.getCallerContinuation();
    }
}
