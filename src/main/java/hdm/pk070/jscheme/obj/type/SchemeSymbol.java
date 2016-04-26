package hdm.pk070.jscheme.obj.type;

import hdm.pk070.jscheme.obj.SchemeObject;

/**
 *
 */
public class SchemeSymbol extends SchemeObject {

    private String symbolVal;

    public SchemeSymbol(String symbolVal) {
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
