package hdm.pk070.jscheme.obj.builtin.syntax.cp.define_cp;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeVoid;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunction;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * Creating a function binding in CP-style. Afterwards, the control flow returns to the caller.
 *
 * @author patrick.kleindienst
 */
@SuppressWarnings("unchecked")
public class SchemeBuiltinSyntaxDefineCP_FunctionBinding extends SchemeContinuationFunction {


    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        SchemeCons functionSignature = (SchemeCons) arguments[0];
        SchemeCons functionBodyList = (SchemeCons) arguments[1];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[2];

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

        continuation.getCallerContinuation().setReturnValue(new SchemeVoid());
        return continuation.getCallerContinuation();
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
