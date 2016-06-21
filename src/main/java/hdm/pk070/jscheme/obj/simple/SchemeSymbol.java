package hdm.pk070.jscheme.obj.simple;

import hdm.pk070.jscheme.obj.SchemeObject;

/**
 *
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
