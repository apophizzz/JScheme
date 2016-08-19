package hdm.pk070.jscheme.cp.trampoline;

import hdm.pk070.jscheme.cp.JSchemeCP;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.print.SchemePrint;
import hdm.pk070.jscheme.reader.SchemeReader;

/**
 * @author patrick.kleindienst
 */
public class SchemeTrampoline {

    public static void next(SchemeContinuation continuation) {
        SchemeContinuation nextContinuation = continuation;

        for (; ; ) {
            SchemeContinuationFunction nextFunction = nextContinuation.getProgramCounter();
            try {
                nextContinuation = nextFunction.call(nextContinuation);
            } catch (SchemeError schemeError) {
                SchemeReader.withCurrentStream().ifPresent(SchemeReader::clear);
                SchemePrint.showError(schemeError);
                break;
            }
        }
    }
}
