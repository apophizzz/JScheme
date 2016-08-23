package hdm.pk070.jscheme.obj.builtin.syntax.cp.quote_cp;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.syntax.cp.SchemeBuiltinSyntaxCP;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;

/**
 * Quoting input in CP-style.
 *
 * @author patrick.kleindienst
 */
public class SchemeBuiltinSyntaxQuoteCP extends SchemeBuiltinSyntaxCP {

    public static SchemeBuiltinSyntaxQuoteCP create() {
        return new SchemeBuiltinSyntaxQuoteCP();
    }

    private SchemeBuiltinSyntaxQuoteCP() {
        super("quote");
    }

    @Override
    public SchemeContinuation apply(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        SchemeObject argumentList = (SchemeObject) arguments[0];

        if (!argumentList.typeOf(SchemeCons.class)) {
            throw new SchemeError("(quote): bad syntax in: (quote) [expected 1 argument, 0 given]");
        } else if (!((SchemeCons) argumentList).getCdr().typeOf(SchemeNil.class)) {
            throw new SchemeError("(quote): bad syntax in: (quote) [expected 1 argument, more given]");
        }
        continuation.getCallerContinuation().setReturnValue(((SchemeCons) argumentList).getCar());
        return continuation.getCallerContinuation();
    }
}
