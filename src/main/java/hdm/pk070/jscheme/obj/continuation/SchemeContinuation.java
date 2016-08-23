package hdm.pk070.jscheme.obj.continuation;

import hdm.pk070.jscheme.cp.trampoline.SchemeTrampoline;
import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;

/**
 * This class represents a continuation for realizing a program flow based on CP-style. Direct function calls are
 * replaced by continuation objects being returned to {@link SchemeTrampoline}, which in turn extracts and invokes
 * the next available program counter set at the current continuation. A continuation object can be regarded as an
 * equivalent to a stack frame.
 *
 * @author patrick.kleindienst
 */
public class SchemeContinuation {

    private SchemeContinuation callerContinuation = null;
    private SchemeContinuationFunction programCounter = null;
    private Object[] arguments = null;
    private SchemeObject returnValue = null;


    public static SchemeContinuation create(SchemeContinuation callerContinuation,
                                            SchemeContinuationFunction programCounter, Object... arguments) {
        return new SchemeContinuation(callerContinuation, programCounter, arguments);
    }


    private SchemeContinuation(SchemeContinuation callerContinuation, SchemeContinuationFunction
            programCounter, Object[] arguments) {
        this.callerContinuation = callerContinuation;
        this.programCounter = programCounter;
        this.arguments = arguments;
    }


    public SchemeContinuationFunction getProgramCounter() {
        return programCounter;
    }

    public SchemeContinuation getCallerContinuation() {
        return callerContinuation;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public SchemeObject getReturnValue() {
        return returnValue;
    }

    public void setProgramCounter(SchemeContinuationFunction programCounter) {
        this.programCounter = programCounter;
    }

    public void setReturnValue(SchemeObject returnValue) {
        this.returnValue = returnValue;
    }

    public void setArguments(Object... arguments) {
        this.arguments = arguments;
    }
}
