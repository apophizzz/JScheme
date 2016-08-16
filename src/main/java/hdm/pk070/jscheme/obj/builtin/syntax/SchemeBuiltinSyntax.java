package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * A base class for every syntax feature coming with JScheme.
 *
 * @author patrick.kleindienst
 */
public abstract class SchemeBuiltinSyntax extends SchemeObject {

    private String internalName;

    protected SchemeBuiltinSyntax(String internalName) {
        this.internalName = internalName;
    }

    /**
     * Applying the built-in syntax to the given parameters.
     *
     * @param argumentList
     *         A list of arguments the syntax shall be applied to.
     * @param environment
     *         The environment within which the arguments shall be evaluated.
     * @return The result of applying a certain syntax on a given argument list.
     * @throws SchemeError
     *         If syntax is not invoked as expected.
     */
    public abstract SchemeObject apply(SchemeObject argumentList, Environment<SchemeSymbol, EnvironmentEntry>
            environment) throws SchemeError;

    /**
     * We assume that a built-in syntax does not have a value to return.
     * The current solution might not be the best one.
     *
     * @return Nothing, always throws {@link UnsupportedOperationException} instead.
     */
    @Override
    public Object getValue() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "<syntax:" + this.internalName + ">";
    }
}
