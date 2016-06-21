package hdm.pk070.jscheme.util.exception;

import hdm.pk070.jscheme.util.ReflectionUtils;

/**
 * A general-purpose exception for {@link ReflectionUtils}.
 *
 * @author patrick.kleindienst
 */
public class ReflectionUtilsException extends RuntimeException {

    public ReflectionUtilsException(String message) {
        super(message);
    }
}
