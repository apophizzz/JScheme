package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.error.SchemeError;

import java.util.Optional;

/**
 * Created by patrick on 17.06.16.
 */
public interface Environment<KEY, VALUE> {

    Optional<VALUE> get(KEY key);

    VALUE add(VALUE value) throws SchemeError;

}
