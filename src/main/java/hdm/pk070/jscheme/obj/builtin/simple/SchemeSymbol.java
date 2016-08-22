package hdm.pk070.jscheme.obj.builtin.simple;

import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * A JScheme symbol which is used for defining variable and function bindings.
 *
 * @author patrick.kleindienst
 */
public class SchemeSymbol extends SchemeObject {

    private final String symbolVal;


    public SchemeSymbol(final String symbolVal) {
        this.symbolVal = symbolVal;
    }

    @Override
    public String getValue() {
        return symbolVal;
    }

    @Override
    public String toString() {
        return symbolVal;
    }
}
