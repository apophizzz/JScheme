package hdm.pk070.jscheme.obj.builtin.syntax.cp.define_cp;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeVoid;
import hdm.pk070.jscheme.obj.builtin.syntax.SchemeBuiltinDefine;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.obj.custom.SchemeCustomUserFunction;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * @author patrick.kleindienst
 */
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

        SchemeBuiltinDefine.create().ensureLastBodyListIsExpression(functionBodyList);

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
}
