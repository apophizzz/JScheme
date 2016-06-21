package hdm.pk070.jscheme.util.exception;

import hdm.pk070.jscheme.util.ReflectionUtils;

/**
 * A custom exception type for {@link ReflectionUtils}
 *
 * @author patrick.kleindienst
 */
public class ReflectionMethodCallException extends RuntimeException {

    public ReflectionMethodCallException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
