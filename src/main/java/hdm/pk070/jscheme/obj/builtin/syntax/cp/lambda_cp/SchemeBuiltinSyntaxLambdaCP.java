package hdm.pk070.jscheme.obj.builtin.syntax.cp.lambda_cp;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.syntax.cp.SchemeBuiltinSyntaxCP;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunction;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
public class SchemeBuiltinSyntaxLambdaCP extends SchemeBuiltinSyntaxCP {


    public static SchemeBuiltinSyntaxLambdaCP create() {
        return new SchemeBuiltinSyntaxLambdaCP();
    }

    private SchemeBuiltinSyntaxLambdaCP() {
        super("lambda");
    }

    @Override
    public SchemeContinuation apply(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        SchemeObject argumentList = (SchemeObject) arguments[0];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[1];

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

        continuation.getCallerContinuation().setReturnValue(SchemeCustomUserFunction.create("anonymous lambda",
                lambdaParameterList, (SchemeCons) lambdaBodyList,
                environment).prepare());

        return continuation.getCallerContinuation();
    }
}
