package hdm.pk070.jscheme.obj.custom;

import hdm.pk070.jscheme.obj.SchemeFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.environment.Environment;

/**
 * @author patrick.kleindienst
 */
public final class SchemeCustomUserFunction extends SchemeFunction {


    private final SchemeObject parameterList;
    private final SchemeCons functionBodyList;
    private final Environment homeEnvironment;
    private int localVariableCount;

    public static SchemeCustomUserFunction create(String internalName, SchemeObject parameterList, SchemeCons
            functionBodyList, Environment homeEnvironment) {
        return new SchemeCustomUserFunction(internalName, parameterList, functionBodyList, homeEnvironment);
    }

    private SchemeCustomUserFunction(String internalName, SchemeObject parameterList, SchemeCons functionBodyList,
                                     Environment homeEnvironment) {
        super(internalName);
        this.parameterList = parameterList;
        this.functionBodyList = functionBodyList;
        this.homeEnvironment = homeEnvironment;
    }

    public SchemeCustomUserFunction prepare() {
        this.localVariableCount = countDefinitions();
        return this;
    }

    /**
     * Iterate through the function's body list and count all present define statements.
     *
     * @return The number of define statements found in the body list
     */
    private int countDefinitions() {
        int currentCount = 0;
        SchemeObject currentBodyPart = this.functionBodyList;

        while (!currentBodyPart.typeOf(SchemeNil.class)) {

            if (currentBodyPart.typeOf(SchemeCons.class)) {
                if (isDefinition(((SchemeCons) currentBodyPart).getCar())) {
                    currentCount++;
                }
                currentBodyPart = ((SchemeCons) currentBodyPart).getCdr();

            } else {
                return 0;
            }
        }
        return currentCount;
    }

    /**
     * Get the n-th part of the function's body and check if it's a define statement.
     *
     * @param partialBody
     *         A part of the function's body list
     * @return True if partialBody is a define statement, false otherwise
     */
    private boolean isDefinition(SchemeObject partialBody) {
        if (partialBody.typeOf(SchemeCons.class)) {
            if (((SchemeCons) partialBody).getCar().equals(new SchemeSymbol("define"))) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
