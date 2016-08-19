package hdm.pk070.jscheme.obj.builtin.syntax.cp.if_cp;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.cp.SchemeEvalCP;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.syntax.cp.SchemeBuiltinSyntaxCP;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
public class SchemeBuiltinSyntaxIfCP extends SchemeBuiltinSyntaxCP {

    public static SchemeBuiltinSyntaxIfCP create() {
        return new SchemeBuiltinSyntaxIfCP();
    }

    private SchemeBuiltinSyntaxIfCP() {
        super("if");
    }

    @Override
    public SchemeContinuation apply(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        SchemeObject argumentList = (SchemeObject) arguments[0];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[1];

        if (!argumentList.typeOf(SchemeCons.class)) {
            throw new SchemeError("(if): bad syntax, has 0 parts after keyword in: (if)");
        }

        SchemeObject conditionalExpression = ((SchemeCons) argumentList).getCar();
        SchemeObject argumentRestList = ((SchemeCons) argumentList).getCdr();

        if (!argumentRestList.typeOf(SchemeCons.class)) {
            throw new SchemeError(String.format("(if): bad syntax, has 1 parts after keyword in: %s", (new SchemeCons
                    (new SchemeSymbol("if"), argumentList)).toString().substring(1)));
        }

        SchemeObject conditionMetExpression = ((SchemeCons) argumentRestList).getCar();
        argumentRestList = ((SchemeCons) argumentRestList).getCdr();

        if (!argumentRestList.typeOf(SchemeCons.class)) {
            throw new SchemeError(String.format("(if): missing 'else' expression in: %s", (new SchemeCons
                    (new SchemeSymbol("if"), argumentList)).toString().substring(1)));
        }

        SchemeObject elseExpression = ((SchemeCons) argumentRestList).getCar();
        argumentRestList = ((SchemeCons) argumentRestList).getCdr();

        if (!argumentRestList.typeOf(SchemeNil.class)) {
            throw new SchemeError(String.format("(if): bad syntax, has too many parts after keyword in: %s", (new
                    SchemeCons(new SchemeSymbol("if"), argumentList)).toString().substring(1)));
        }

        continuation.setProgramCounter(new SchemeBuiltinSyntaxIfCP2());
        continuation.setArguments(conditionMetExpression, elseExpression, environment);

        return SchemeContinuation.create(continuation, SchemeEvalCP.getInstance(), conditionalExpression, environment);

    }
}
