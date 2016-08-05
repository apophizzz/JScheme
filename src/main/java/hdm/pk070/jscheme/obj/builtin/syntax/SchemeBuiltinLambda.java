package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunction;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
public class SchemeBuiltinLambda extends SchemeBuiltinSyntax {


    public static SchemeBuiltinLambda create() {
        return new SchemeBuiltinLambda();
    }

    private SchemeBuiltinLambda() {
        super("lambda");
    }

    @Override
    public SchemeObject apply(SchemeObject argumentList, Environment<SchemeSymbol, EnvironmentEntry> environment)
            throws SchemeError {
        if (!argumentList.typeOf(SchemeCons.class)) {
            throw new SchemeError("(lambda): bad syntax in: (lambda) [expected 2 arguments, 0 given]");
        }

        SchemeObject lambdaParameterList = ((SchemeCons) argumentList).getCar();

        if (!(lambdaParameterList.typeOf(SchemeNil.class) || lambdaParameterList.typeOf(SchemeCons.class) ||
                lambdaParameterList.typeOf(SchemeSymbol.class))) {
            throw new SchemeError("(lambda): bad syntax, invalid parameter list");
        }

        SchemeObject lambdaBodyList = ((SchemeCons) argumentList).getCdr();

        if (!lambdaBodyList.typeOf(SchemeCons.class)) {
            throw new SchemeError("(lambda): bad syntax in: (lambda (x)) [expected 2 arguments, 1 given]");
        }

        return SchemeCustomUserFunction.create("anonymous lambda", lambdaParameterList, (SchemeCons) lambdaBodyList,
                environment).prepare();
    }
}
