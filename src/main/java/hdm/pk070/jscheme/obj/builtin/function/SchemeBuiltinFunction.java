package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeFunction;
import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * Created by patrick on 19.06.16.
 */
public abstract class SchemeBuiltinFunction extends SchemeFunction {


    protected SchemeBuiltinFunction(final String internalName) {
        super(internalName);
    }

    public abstract SchemeObject call(int argCount) throws SchemeError;

}
