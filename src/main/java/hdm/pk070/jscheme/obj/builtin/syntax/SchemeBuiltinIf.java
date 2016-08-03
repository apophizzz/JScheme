package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.SchemeEval;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeTrue;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
public final class SchemeBuiltinIf extends SchemeBuiltinSyntax {

    public static SchemeBuiltinIf create() {
        return new SchemeBuiltinIf();
    }

    private SchemeBuiltinIf() {
        super("if");
    }

    @Override
    public SchemeObject apply(SchemeObject argumentList, Environment<SchemeSymbol, EnvironmentEntry> environment)
            throws SchemeError {

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

        SchemeObject conditionalValue = SchemeEval.getInstance().eval(conditionalExpression, environment);
        if (isNonZeroNumber(conditionalValue) || isNonEmptyString(conditionalValue) || isSchemeTrue(conditionalValue)) {
            return SchemeEval.getInstance().eval(conditionMetExpression, environment);
        } else {
            return SchemeEval.getInstance().eval(elseExpression, environment);
        }
    }

    private boolean isNonZeroNumber(SchemeObject conditionValue) {
        return conditionValue.subtypeOf(SchemeNumber.class) && (((SchemeNumber) conditionValue).getValue()
                .intValue() != 0);
    }

    private boolean isNonEmptyString(SchemeObject conditionValue) {
        return conditionValue.typeOf(SchemeString.class) && !((SchemeString) conditionValue).getValue().equals("");
    }

    private boolean isSchemeTrue(SchemeObject conditionValue) {
        return conditionValue.typeOf(SchemeTrue.class);
    }
}
