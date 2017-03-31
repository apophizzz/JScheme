package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.SchemeEval;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeVoid;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunction;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

import java.util.Objects;

/**
 * Create a binding between a {@link SchemeSymbol} and a variable or rather a function
 * in a local or global environment.
 * <br/><br/>
 * Usage examples:
 * <br/><br/>
 * (define abc 123)<br/>
 * (define abc (+ 42 1))<br/>
 * (define (add1 x) (+ 1 x))<br/>
 *
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
            return this.createFunctionBinding((SchemeCons) argListCar, argListCdr, environment);
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

    private SchemeVoid createFunctionBinding(SchemeCons functionSignature, SchemeCons functionBodyList,
                                             Environment<SchemeSymbol,
                                                     EnvironmentEntry> environment) throws SchemeError {

        if (!functionSignature.getCar().typeOf(SchemeSymbol.class)) {
            throw new SchemeError(String.format("(define): bad syntax (not an identifier for procedure name: %s)",
                    functionSignature.getCar()));
        }

        if (functionBodyList.getCar().equals(new SchemeNil())) {
            throw new SchemeError("(define): missing procedure expression");
        }

        ensureLastBodyListIsExpression(functionBodyList);

        // Extract function name from signature after having ensured that the signature's CAR is actually a symbol
        SchemeSymbol functionName = (SchemeSymbol) functionSignature.getCar();

        // Extract param list from signature
        SchemeObject functionParamList = functionSignature.getCdr();

        // Create user-defined function
        SchemeCustomUserFunction customUserFunction = SchemeCustomUserFunction.create(functionName.getValue(),
                functionParamList, functionBodyList, environment).prepare();

        // Add new function to environment
        environment.add(EnvironmentEntry.create(functionName, customUserFunction));

        return new SchemeVoid();
    }

    /**
     * Inspect function body and make sure that the last partial body list is an expression. If it's a define
     * statement instead, a {@link SchemeError} is thrown.
     *
     * @param functionBody
     *         The function body that shall be checked.
     * @throws SchemeError
     *         If the syntax rules are violated.
     */
    private void ensureLastBodyListIsExpression(SchemeCons functionBody) throws SchemeError {
        SchemeObject restBodyLists = functionBody;

        while (restBodyLists.typeOf(SchemeCons.class) && !((SchemeCons) restBodyLists).getCdr().typeOf(SchemeNil
                .class)) {
            restBodyLists = ((SchemeCons) restBodyLists).getCdr();
        }

        SchemeObject lastBodyList = ((SchemeCons) restBodyLists).getCar();
        if (lastBodyList.typeOf(SchemeCons.class) && ((SchemeCons) lastBodyList).getCar().equals(new SchemeSymbol
                ("define"))) {
            throw new SchemeError("(define): no expression after sequence of " +
                    "internal definitions");
        }
    }


}
