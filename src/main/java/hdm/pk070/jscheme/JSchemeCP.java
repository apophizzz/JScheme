package hdm.pk070.jscheme;

import hdm.pk070.jscheme.cp.SchemeFinish;
import hdm.pk070.jscheme.cp.repl.SchemeREPL;
import hdm.pk070.jscheme.cp.trampoline.SchemeTrampoline;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.continuation.SchemeContinuation;
import hdm.pk070.jscheme.setup.JSchemeSetup;

/**
 * Start CPS-flavoured JScheme and enter trampoline. We need a trampoline here because Assembler-like jumps are not
 * possible in Java.
 *
 * @author patrick.kleindienst
 */
class JSchemeCP {

    public static void main(String[] args) throws SchemeError {
        JSchemeSetup.init();
        startup();
    }


    @SuppressWarnings("InfiniteLoopStatement")
    private static void startup() {
        SchemeContinuation initialCallerContinuation = SchemeContinuation.create(null, new SchemeFinish());
        SchemeContinuation nextContinuation = SchemeContinuation.create(initialCallerContinuation, new
                SchemeREPL());
        for (; ; ) {
            SchemeTrampoline.next(nextContinuation);
            nextContinuation.setProgramCounter(new SchemeREPL());
        }
    }


}


