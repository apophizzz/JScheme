package hdm.pk070.jscheme.obj.type.function.builtin;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * Created by patrick on 19.06.16.
 */
public abstract class SchemeBuiltinFunction extends SchemeObject {

    private String internalName;

    protected SchemeBuiltinFunction(String internalName) {
        this.internalName = internalName;
    }

    public String getInternalName() {
        return internalName;
    }

    public abstract SchemeObject call(int argCount) throws SchemeError;


    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public Object getValue() {
        throw new UnsupportedOperationException();
    }
}
