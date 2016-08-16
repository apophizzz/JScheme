package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * Built-in syntax 'quote' takes a single argument and returns it without evaluation. That means that the return
 * value matches the reader's output.
 *
 * @author patrick.kleindienst
 */
public final class SchemeBuiltinQuote extends SchemeBuiltinSyntax {


    public static SchemeBuiltinQuote create() {
        return new SchemeBuiltinQuote();
    }

    private SchemeBuiltinQuote() {
        super("quote");
    }

    @Override
    public SchemeObject apply(SchemeObject argumentList, Environment<SchemeSymbol, EnvironmentEntry> environment)
            throws SchemeError {

        if (!argumentList.typeOf(SchemeCons.class)) {
            throw new SchemeError("(quote): bad syntax in: (quote) [expected 1 argument, 0 given]");
        } else if (!((SchemeCons) argumentList).getCdr().typeOf(SchemeNil.class)) {
            throw new SchemeError("(quote): bad syntax in: (quote) [expected 1 argument, more given]");
        }
        return ((SchemeCons) argumentList).getCar();
    }
}
