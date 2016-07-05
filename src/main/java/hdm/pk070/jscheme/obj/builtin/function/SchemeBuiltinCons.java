package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.stack.SchemeCallStack;

import java.util.Objects;

/**
 * @author patrick.kleindienst
 */
public final class SchemeBuiltinCons extends SchemeBuiltinFunction {

    public static SchemeBuiltinCons create() {
        return new SchemeBuiltinCons("cons");
    }

    private SchemeBuiltinCons(String internalName) {
        super(internalName);
    }

    @Override
    public SchemeObject call(int argCount) throws SchemeError {
        if (argCount != 2) {
            throw new SchemeError(String.format("(cons): arity mismatch, expected number of arguments does not match " +
                    "given number [expected: 2, given: %d]", argCount));
        }

        // switch order of argument
        SchemeObject arg2 = SchemeCallStack.instance().pop();
        SchemeObject arg1 = SchemeCallStack.instance().pop();

        return new SchemeCons(arg1, arg2);
    }

}
