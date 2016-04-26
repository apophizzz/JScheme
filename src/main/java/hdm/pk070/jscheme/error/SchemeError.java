package hdm.pk070.jscheme.error;

/**
 *
 */
public class SchemeError {

    private String message;


    public static void print(String message) {
        new SchemeError(message).printMessage();
    }

    private SchemeError(String message) {
        this.message = message;
    }

    private void printMessage() {
        System.err.println("ERROR: " + message + "\n");
    }


}
