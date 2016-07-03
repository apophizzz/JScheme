package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeFraction;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.stack.SchemeCallStack;
import org.apache.commons.math3.fraction.Fraction;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by patrick on 24.06.16.
 */
public final class SchemeBuiltinDivide extends SchemeBuiltinFunction {


    public static SchemeBuiltinDivide create() {
        return new SchemeBuiltinDivide("/");
    }

    private SchemeBuiltinDivide(String internalName) {
        super(internalName);
    }

    @Override
    public SchemeNumber call(int argCount) throws SchemeError {
        SchemeNumber divisionResult;

        // Division expects at least one argument
        if (argCount == 0) {
            throw new SchemeError("(/): arity mismatch, expected number of arguments does not match given number " +
                    "[expected: at least 1, given 0]");
        } else if (argCount == 1) {
            SchemeObject poppedArg = SchemeCallStack.instance().pop();

            // Check if popped arg is number
            if (!poppedArg.subtypeOf(SchemeNumber.class)) {
                throw new SchemeError(String.format("(/): contract violation [expected: number, given: %s]",
                        poppedArg));
            }
            return new SchemeInteger(1).divide(((SchemeNumber) poppedArg));
        } else {

            List<SchemeNumber> argList = new LinkedList<>();
            for (int i = 0; i < argCount; i++) {
                SchemeObject poppedArg = SchemeCallStack.instance().pop();
                if (!poppedArg.subtypeOf(SchemeNumber.class)) {
                    throw new SchemeError(String.format("(/): contract violation [expected: number, given: %s]",
                            poppedArg));
                }
                argList.add((SchemeNumber) poppedArg);
            }

            // Invert order of arguments from stack since we need them in reverse order for division
            Collections.reverse(argList);

            // Start with first argument
            divisionResult = argList.remove(0);

            for (SchemeNumber currentArg : argList) {
                divisionResult = divisionResult.divide(currentArg);
            }

            return divisionResult;
        }
    }

    @Override
    public String toString() {
        return "<procedure:/>";
    }
}
