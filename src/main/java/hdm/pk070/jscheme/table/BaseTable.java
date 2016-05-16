package hdm.pk070.jscheme.table;

import hdm.pk070.jscheme.error.SchemeError;

import java.util.Optional;

/**
 * @author patrick.kleindienst
 */
abstract class BaseTable<KEY, VALUE> {

    public abstract Optional<VALUE> get(KEY key);

    public abstract VALUE add(VALUE value) throws SchemeError;

}
