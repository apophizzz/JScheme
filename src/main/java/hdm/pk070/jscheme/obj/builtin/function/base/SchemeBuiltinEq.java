package hdm.pk070.jscheme.obj.builtin.function.base;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeBool;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeFalse;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeTrue;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * This class' purpose is to take exactly two arguments and check them for equality.
 * In this case, 'equality' is defined as follows:
 * <br>
 *
 * <ul>
 * <li>Two numbers (integers, floats, ...) are considered equal if the have exactly the same value.</li>
 * <p>
 * >> (eq? 1 1) <br>
 * => #t
 * </p>
 *
 * <li>Two strings are considered equal if they consist of exactly the same character sequence.</li>
 * <p>
 * >> (eq? "test" "test")<br>
 * => #t<br>
 * >> (eq? "first" "second")<br>
 * => #f
 * </p>
 *
 * <li>Two symbols are considered equal if the values they refer to are equal.</li>
 * <p>
 * >> (define a "first")<br>
 * >> (define b "second")<br>
 * >> (eq? a b)<br>
 * => #f<br>
 * >> (eq? a a)<br>
 * => #t
 * </p>
 *
 * <li>Two lists can never be equal, since they're actually different scheme objects, no matter if they contain
 * exactly the same elements.</li>
 * <p>
 * >> (define a '(1 2 3))<br>
 * >> (define b '(1 2 3))<br>
 * >> (eq? a b)<br>
 * => #f
 * </p>
 *
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
