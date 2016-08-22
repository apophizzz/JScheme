package hdm.pk070.jscheme.error;

/**
 * This special exception type is used to communicate all types of errors concerning
 * JScheme usage.
 *
 * @author patrick.kleindienst
 */
public class SchemeError extends Exception {

    public SchemeError(String message) {
        super(message);
    }


}
