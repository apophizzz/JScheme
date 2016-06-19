package hdm.pk070.jscheme.table;

import hdm.pk070.jscheme.error.SchemeError;

import java.util.Optional;

/**
 * @author patrick.kleindienst
 */
abstract class BaseTable<KEY, VALUE> {

    public abstract Optional<VALUE> get(final KEY key);

    public abstract VALUE add(final VALUE value) throws SchemeError;

    protected abstract boolean keysMatch(final KEY key, final VALUE entryFound);

}
