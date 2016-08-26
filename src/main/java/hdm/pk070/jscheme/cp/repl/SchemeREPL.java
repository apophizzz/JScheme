package hdm.pk070.jscheme.cp.repl;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.cp.SchemeEvalCP;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.print.SchemePrint;
import hdm.pk070.jscheme.reader.SchemeReader;
import hdm.pk070.jscheme.table.environment.GlobalEnvironment;

/**
 * This is the entry point for JScheme REPL.
 *
 * @author patrick.kleindienst
 */
public class SchemeREPL extends SchemeContinuationFunction {

    @Override
    public SchemeContinuation call(SchemeContinuation continuation) throws SchemeError {

        SchemePrint.showPrompt();

        SchemeReader schemeReader = SchemeReader.withStdin();
        SchemeObject readResult;

        readResult = schemeReader.read();

        continuation.setProgramCounter(new SchemeREPL2());
        return SchemeContinuation.create(continuation, SchemeEvalCP.getInstance(), readResult,
                GlobalEnvironment.getInstance());
    }
}
