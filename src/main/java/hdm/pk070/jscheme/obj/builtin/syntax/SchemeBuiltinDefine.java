package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.SchemeEval;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeVoid;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

import java.util.Objects;

/**
 * @author patrick.kleindienst
 */
public final class SchemeBuiltinDefine extends SchemeBuiltinSyntax {

    public static SchemeBuiltinDefine create() {
        return new SchemeBuiltinDefine();
    }

    private SchemeBuiltinDefine() {
        super("define");
    }

    @Override
    public SchemeObject apply(SchemeObject argumentList, Environment<SchemeSymbol, EnvironmentEntry> environment)
            throws SchemeError {

        Objects.requireNonNull(argumentList);
        Objects.requireNonNull(environment);

        // (define abc 123)
        // (define (add1to x) (+ 1 x))
        // Argument list must be a cons
        if (!argumentList.typeOf(SchemeCons.class) || !((SchemeCons) argumentList).getCdr().typeOf(SchemeCons.class)) {
            throw new SchemeError("(define): bad syntax (requires exactly 2 arguments)");
        }

        // The name of the variable/function
        SchemeObject argListCar = ((SchemeCons) argumentList).getCar();

        // The value the name gets bound to (simple object or function body)
        SchemeCons argListCdr = (SchemeCons) ((SchemeCons) argumentList).getCdr();

        if (argListCar.typeOf(SchemeSymbol.class)) {
            // We have a variable binding
            return this.createVariableBinding(((SchemeSymbol) argListCar), argListCdr, environment);
        } else if (argListCar.typeOf(SchemeCons.class)) {
            // We have a function binding
            return null;
        }

        throw new SchemeError("(define): bad syntax");
    }

    private SchemeVoid createVariableBinding(SchemeSymbol variableName, SchemeCons valueCons, Environment<SchemeSymbol,
            EnvironmentEntry> environment) throws SchemeError {

        if (!valueCons.getCdr().typeOf(SchemeNil.class)) {
            // throw SchemeError if cdr of argument list is followed by anything else than nil
            throw new SchemeError("(define): bad syntax (multiple expressions after identifier)");
        }

        SchemeObject variableValue = valueCons.getCar();
        environment.add(EnvironmentEntry.create(variableName, SchemeEval.getInstance().eval(variableValue,
                environment)));
        return new SchemeVoid();
    }
}