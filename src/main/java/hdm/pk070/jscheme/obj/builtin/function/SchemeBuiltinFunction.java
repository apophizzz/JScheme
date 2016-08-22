package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.stack.SchemeCallStack;

/**
 * This class acts as an abstraction for all implementations of built-in JScheme functions.
 *
 * @author patrick.kleindienst
 */
public abstract class SchemeBuiltinFunction extends SchemeFunction {


    protected SchemeBuiltinFunction(final String internalName) {
        super(internalName);
    }

    /**
     * Calling a built-in function.
     *
     * @param argCount
     *         The number of arguments which have to be fetched from {@link SchemeCallStack}.
     * @return The result of the function call.
     * @throws SchemeError
     *         If anything goes wrong during function call (e.g. wrong number or type of arguments).
     */
    public abstract SchemeObject call(int argCount) throws SchemeError;

}
