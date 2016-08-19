package hdm.pk070.jscheme.obj.continuation;

import hdm.pk070.jscheme.obj.SchemeContinuationFunction;
import hdm.pk070.jscheme.obj.SchemeObject;

/**
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
