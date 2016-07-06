package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
public abstract class SchemeBuiltinSyntax extends SchemeObject {

    private String internalName;

    protected SchemeBuiltinSyntax(String internalName) {
        this.internalName = internalName;
    }

    public abstract SchemeObject apply(SchemeObject argumentList, Environment<SchemeSymbol, EnvironmentEntry>
            environment) throws SchemeError;

    @Override
    public Object getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "<syntax:" + this.internalName + ">";
    }
}
