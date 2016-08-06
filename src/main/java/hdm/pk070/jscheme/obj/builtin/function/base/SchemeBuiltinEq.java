package hdm.pk070.jscheme.obj.builtin.function.base;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeBool;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeFalse;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeTrue;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * @author patrick.kleindienst
 */
public class SchemeBuiltinEq extends SchemeBuiltinFunction {

    public static SchemeBuiltinEq create() {
        return new SchemeBuiltinEq();
    }

    private SchemeBuiltinEq() {
        super("eq?");
    }

    @Override
    public SchemeBool call(int argCount) throws SchemeError {
        if (argCount != 2) {
            throw new SchemeError(String.format("(eq?): arity mismatch, expected number of arguments does not match " +
                    "the given" + " number [expected: 2, given: %d]", argCount));
        }

        SchemeObject argument2 = SchemeCallStack.instance().pop();
        SchemeObject argument1 = SchemeCallStack.instance().pop();

        if (argument2 == argument1) {
            return new SchemeTrue();
        } else if (!argument2.typeOf(SchemeCons.class) && argument2.equals(argument1)) {
            return new SchemeTrue();
        } else {
            return new SchemeFalse();
        }
    }
}
