package hdm.pk070.jscheme.obj.builtin.simple.bool;

import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;

/**
 * A base class for {@link SchemeTrue} and {@link SchemeFalse}.
 *
 * @author patrick.kleindienst
 */
public abstract class SchemeBool extends SchemeSymbol {

    protected SchemeBool(String symbolVal) {
        super(symbolVal);
    }
}
