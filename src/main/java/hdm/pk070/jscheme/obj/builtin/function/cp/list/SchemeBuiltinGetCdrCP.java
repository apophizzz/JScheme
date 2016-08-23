package hdm.pk070.jscheme.obj.builtin.function.cp.list;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunctionCP;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * Built-in "cdr" in CP-style.
 *
 * @author patrick.kleindienst
 */
public class SchemeBuiltinGetCdrCP extends SchemeBuiltinFunctionCP {


    public static SchemeBuiltinGetCdrCP create() {
        return new SchemeBuiltinGetCdrCP();
    }

    private SchemeBuiltinGetCdrCP() {
        super("cdr");
    }

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        int argCount = (int) arguments[0];

        if (argCount != 1) {
            throw new SchemeError(String.format("(cdr): arity mismatch, expected number of arguments does not match " +
                    "given number [expected: 1, given: %d]", argCount));
        }

        SchemeObject poppedArg = SchemeCallStack.instance().pop();
        if (!poppedArg.typeOf(SchemeCons.class)) {
            throw new SchemeError(String.format("(cdr): contract violation [expected: cons, given: %s]",
                    poppedArg));
        }

        continuation.getCallerContinuation().setReturnValue(((SchemeCons) poppedArg).getCdr());
        return continuation.getCallerContinuation();
    }
}
