package hdm.pk070.jscheme.obj.builtin.function.cp.list;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunctionCP;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeFalse;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeTrue;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * @author patrick.kleindienst
 */
public class SchemeBuiltinIsConsCP extends SchemeBuiltinFunctionCP {


    public static SchemeBuiltinIsConsCP create() {
        return new SchemeBuiltinIsConsCP();
    }

    private SchemeBuiltinIsConsCP() {
        super("cons?");
    }

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        int argCount = (int) arguments[0];

        if (argCount != 1) {
            throw new SchemeError(String.format("(cons?): arity mismatch, expected number of arguments does not match" +
                    " " +
                    "given number [expected: 1, given: %d]", argCount));
        }

        SchemeObject poppedArg = SchemeCallStack.instance().pop();

        if (poppedArg.typeOf(SchemeCons.class)) {
            continuation.getCallerContinuation().setReturnValue(new SchemeTrue());
        } else {
            continuation.getCallerContinuation().setReturnValue(new SchemeFalse());
        }

        return continuation.getCallerContinuation();
    }
}
