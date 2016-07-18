package hdm.pk070.jscheme.obj.custom;

import hdm.pk070.jscheme.obj.SchemeFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.table.environment.Environment;

/**
 * @author patrick.kleindienst
 */
public final class SchemeCustomUserFunction extends SchemeFunction {


    private final SchemeObject parameterList;
    private final SchemeObject functionBody;
    private final Environment homeEnvironment;
    private final int localVariableCount;

    public static SchemeCustomUserFunction create(String internalName, SchemeObject parameterList, SchemeObject
            functionBody, Environment homeEnvironment) {
        return new SchemeCustomUserFunction(internalName, parameterList, functionBody, homeEnvironment);
    }

    private SchemeCustomUserFunction(String internalName, SchemeObject parameterList, SchemeObject functionBody,
                                     Environment homeEnvironment) {
        super(internalName);
        this.parameterList = parameterList;
        this.functionBody = functionBody;
        this.homeEnvironment = homeEnvironment;
        this.localVariableCount = countDefinitions();
    }

    private int countDefinitions() {
        return 0;
    }

}
