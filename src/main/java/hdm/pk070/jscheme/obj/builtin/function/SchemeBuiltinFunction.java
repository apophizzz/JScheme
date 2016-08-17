package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeFunction;
import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * This class acts as an abstraction for all implementations of built-in JScheme functions.
 *
 * @author patrick.kleindienst
 */
public abstract class SchemeBuiltinFunction extends SchemeFunction {


    protected SchemeBuiltinFunction(final String internalName) {
        super(internalName);
    }

    public abstract SchemeObject call(int argCount) throws SchemeError;

}
