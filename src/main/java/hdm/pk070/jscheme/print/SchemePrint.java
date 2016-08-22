package hdm.pk070.jscheme.print;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeVoid;

/**
 * Deals with any print concerns in JScheme.
 *
 * @author patrick.kleindienst
 */
public final class SchemePrint {

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    public static void showPrompt() {
        System.out.print(">> ");
    }

    /**
     * Take any {@link SchemeObject} and invoke its {@link SchemeObject#toString()} method.
     *
     * @param evalResult
     *         The evaluation result to print.
     */
    public static void printEvalResult(SchemeObject evalResult) {
        if (!evalResult.typeOf(SchemeVoid.class)) {
            System.out.print("=> ");
            System.out.println(evalResult);
        }
    }

    /**
     * Take a {@link SchemeError} and print its error message.
     *
     * @param schemeError
     *         The {@link SchemeError} to print.
     */
    public static void showError(SchemeError schemeError) {
        System.out.println(ANSI_RED + "### ERROR: " + schemeError.getMessage() + ANSI_RESET);
    }
}
