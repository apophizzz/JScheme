package hdm.pk070.jscheme.obj.builtin.syntax.cp.define_cp;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.builtin.syntax.cp.SchemeBuiltinSyntaxCP;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

/**
 * Part 1: Check if "define" has been invoked correctly and delegate the call to the next part, depending on whether
 * a variable or function binding should be created.
 *
 * @author patrick.kleindienst
 */
@SuppressWarnings("unchecked")
public class SchemeBuiltinSyntaxDefineCP extends SchemeBuiltinSyntaxCP {


    public static SchemeBuiltinSyntaxDefineCP create() {
        return new SchemeBuiltinSyntaxDefineCP();
    }

    private SchemeBuiltinSyntaxDefineCP() {
        super("define");
    }

    @Override
    public SchemeContinuation apply(SchemeContinuation continuation) throws SchemeError {
        Object[] arguments = continuation.getArguments();
        SchemeObject argumentList = (SchemeObject) arguments[0];
        Environment<SchemeSymbol, EnvironmentEntry> environment = (Environment<SchemeSymbol, EnvironmentEntry>)
                arguments[1];

        if (!argumentList.typeOf(SchemeCons.class) || !((SchemeCons) argumentList).getCdr().typeOf(SchemeCons.class)) {
            throw new SchemeError("(define): bad syntax (requires exactly 2 arguments)");
        }

        // The name of the variable/function
        SchemeObject argListCar = ((SchemeCons) argumentList).getCar();

        // The value the name gets bound to (simple object or function body)
        SchemeCons argListCdr = (SchemeCons) ((SchemeCons) argumentList).getCdr();

        if (argListCar.typeOf(SchemeSymbol.class)) {
            // We have a variable binding
            continuation.setArguments(argListCar, argListCdr, environment);
            continuation.setProgramCounter(new SchemeBuiltinSyntaxDefineCP_VariableBinding());
            return continuation;

        } else if (argListCar.typeOf(SchemeCons.class)) {
            // We have a function binding
            continuation.setArguments(argListCar, argListCdr, environment);
            continuation.setProgramCounter(new SchemeBuiltinSyntaxDefineCP_FunctionBinding());
            return continuation;
        }

        throw new SchemeError("(define): bad syntax");
    }
}
