package hdm.pk070.jscheme.obj.builtin.function.list;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * This function can be used to create lists consisting of {@link SchemeCons} cells.
 *
 * @author patrick.kleindienst
 */
public final class SchemeBuiltinCons extends SchemeBuiltinFunction {

    public static SchemeBuiltinCons create() {
        return new SchemeBuiltinCons();
    }

    private SchemeBuiltinCons() {
        super("cons");
    }

    @Override
    public SchemeObject call(int argCount) throws SchemeError {
        if (argCount != 2) {
            throw new SchemeError(String.format("(cons): arity mismatch, expected number of arguments does not match " +
                    "given number [expected: 2, given: %d]", argCount));
        }

        // switch order of arguments
        SchemeObject arg2 = SchemeCallStack.instance().pop();
        SchemeObject arg1 = SchemeCallStack.instance().pop();

        return new SchemeCons(arg1, arg2);
    }

}
