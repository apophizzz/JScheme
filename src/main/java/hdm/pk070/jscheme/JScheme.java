package hdm.pk070.jscheme;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.eval.SchemeEval;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.print.SchemePrint;
import hdm.pk070.jscheme.reader.SchemeReader;
import hdm.pk070.jscheme.setup.JSchemeSetup;
import hdm.pk070.jscheme.table.environment.GlobalEnvironment;

/**
 * JScheme starting point
 *
 * @author patrick.kleindienst
 */
class JScheme {

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws SchemeError {

        JSchemeSetup.init();

        SchemeReader schemeReader = SchemeReader.withStdin();
        for (; ; ) {
            SchemePrint.showPrompt();
            try {
                SchemeObject readResult = schemeReader.read();
                SchemeObject evalResult = SchemeEval.getInstance().eval(readResult, GlobalEnvironment.getInstance());
                SchemePrint.printEvalResult(evalResult);
            } catch (SchemeError schemeError) {
                schemeReader.clear();
                SchemePrint.showError(schemeError);
            }
        }
    }

}
