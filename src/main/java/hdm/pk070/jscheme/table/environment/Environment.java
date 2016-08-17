package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.error.SchemeError;

import java.util.Optional;

/**
 * A general interface for all kinds of environments.
 *
 * @author patrick.kleindienst
 */
public interface Environment<KEY, VALUE> {

    Optional<VALUE> get(final KEY key);

    VALUE add(final VALUE value) throws SchemeError;

}
