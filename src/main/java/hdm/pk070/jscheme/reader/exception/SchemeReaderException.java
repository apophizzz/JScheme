package hdm.pk070.jscheme.reader.exception;

import hdm.pk070.jscheme.reader.SchemeReader;

import java.io.InputStream;

/**
 * This {@link RuntimeException} implementation is used for indicating I/O issues with the {@link InputStream}
 * the {@link SchemeReader} consumes its input from.
 *
 * @author patrick.kleindienst
 */
public class SchemeReaderException extends RuntimeException {

    public SchemeReaderException(String message, Throwable cause) {
        super(message, cause);
    }

}
