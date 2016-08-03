package hdm.pk070.jscheme.obj.custom;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeFunction;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.environment.Environment;

/**
 * This class represents a custom Scheme function defined by the user.
 *
 * @author patrick.kleindienst
 */
public class SchemeCustomUserFunction extends SchemeFunction {


    private final SchemeObject parameterList;
    private final SchemeCons functionBodyList;
    private final Environment homeEnvironment;
    private Integer localVariableCount;
    private Integer paramCount;

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
        this.localVariableCount = 0;
        this.paramCount = 0;
    }

    /**
     * Triggering the function to finish its own setup.
     *
     * @return The calling function object
     * @throws SchemeError
     *         re-thrown from {@link #countParams()}
     */
    public SchemeCustomUserFunction prepare() throws SchemeError {
        this.localVariableCount = countDefinitions();
        this.paramCount = countParams();
        return this;
    }

    /**
     * Counting the number of parameters specified in the function's parameter list.
     *
     * @return The parameter count
     * @throws SchemeError
     *         re-thrown from {@link #isValidParam(SchemeObject)}
     */
    private int countParams() throws SchemeError {
        int count = 0;
        SchemeObject paramList = this.parameterList;
        while (!paramList.typeOf(SchemeNil.class)) {
            if (isValidParam(paramList)) {
                if (paramList.typeOf(SchemeSymbol.class)) {
                    return count + 1;
                }
            }
            count++;
            paramList = ((SchemeCons) paramList).getCdr();
        }
        return count;
    }

    /**
     * Check if a single parameter of the list is valid. A parameter is considered valid if it's a cons cell and it's
     * car is symbol.
     *
     * @param parameterList
     *         The parameter to examine
     * @return True if valid
     * @throws SchemeError
     *         if parameter is invalid
     */
    private boolean isValidParam(SchemeObject parameterList) throws SchemeError {
        if (parameterList.typeOf(SchemeCons.class) && ((SchemeCons) parameterList).getCar()
                .typeOf(SchemeSymbol.class)) {
            return true;
        }
        throw new SchemeError(String.format("(define): not an identifier for procedure argument in: %s",
                ((SchemeCons) parameterList).getCar()));
    }

    /**
     * Iterate over the function's body list and count all present define statements.
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
        return partialBody.typeOf(SchemeCons.class) && ((SchemeCons) partialBody).getCar().equals(new
                SchemeSymbol("define"));
    }

    public int getRequiredSlotsCount() {
        return this.paramCount + this.localVariableCount;
    }

    public int getParamCount() {
        return paramCount;
    }

    public Environment getHomeEnvironment() {
        return homeEnvironment;
    }

    public SchemeObject getParameterList() {
        return parameterList;
    }

    public SchemeCons getFunctionBodyList() {
        return functionBodyList;
    }
}
