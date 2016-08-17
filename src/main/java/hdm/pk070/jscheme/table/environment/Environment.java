package hdm.pk070.jscheme.table.environment;

import java.util.Optional;

/**
 * A general interface for all kinds of environments.
 *
 * @author patrick.kleindienst
 */
public interface Environment<KEY, VALUE> {

    Optional<VALUE> get(final KEY key);

    VALUE add(final VALUE value);

}
