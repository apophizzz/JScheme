package hdm.pk070.jscheme.obj.builtin.function.list;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * Returns the CAR of a list.
 *
 * @author patrick.kleindienst
 */
public final class SchemeBuiltinGetCar extends SchemeBuiltinFunction {


    public static SchemeBuiltinGetCar create() {
        return new SchemeBuiltinGetCar();
    }

    private SchemeBuiltinGetCar() {
        super("car");
    }

    @Override
    public SchemeObject call(int argCount) throws SchemeError {
        if (argCount != 1) {
            throw new SchemeError(String.format("(car): arity mismatch, expected number of arguments does not match " +
                    "given number [expected: 1, given: %d]", argCount));
        }

        SchemeObject poppedArg = SchemeCallStack.instance().pop();
        if (!poppedArg.typeOf(SchemeCons.class)) {
            throw new SchemeError(String.format("(car): contract violation [expected: cons, given: %s]",
                    poppedArg));
        }
        return ((SchemeCons) poppedArg).getCar();
    }

}
