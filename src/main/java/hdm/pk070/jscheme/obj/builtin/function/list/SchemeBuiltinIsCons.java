package hdm.pk070.jscheme.obj.builtin.function.list;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeBool;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeFalse;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeTrue;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * @author patrick.kleindienst
 */
public final class SchemeBuiltinIsCons extends SchemeBuiltinFunction {


    public static SchemeBuiltinIsCons create() {
        return new SchemeBuiltinIsCons();
    }

    private SchemeBuiltinIsCons() {
        super("cons?");
    }

    @Override
    public SchemeBool call(int argCount) throws SchemeError {
        if (argCount != 1) {
            throw new SchemeError(String.format("(cons?): arity mismatch, expected number of arguments does not match" +
                    " " +
                    "given number [expected: 1, given: %d]", argCount));
        }
        SchemeObject poppedArg = SchemeCallStack.instance().pop();
        if (poppedArg.typeOf(SchemeCons.class)) {
            return new SchemeTrue();
        }
        return new SchemeFalse();
    }
}
