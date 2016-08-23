package hdm.pk070.jscheme.table;

import hdm.pk070.jscheme.error.SchemeError;

import java.util.Optional;

/**
 * An abstraction for all kinds of tables which are implemented as hash tables and offer basic GET and ADD
 * operations.
 *
 * @author patrick.kleindienst
 */
abstract class BaseTable<KEY, VALUE> {

    /**
     * Retrieve a value from the hash table by means of a {@code key}.
     *
     * @param key
     *         The key to search for.
     * @return An {@link Optional} which either contains the searched value (in case it exists within the table) or
     * is empty.
     */
    public abstract Optional<VALUE> get(final KEY key);

    /**
     * Adding a value to the hash table.
     *
     * @param value
     *         The value that shall be saved.
     * @return The saved value.
     * @throws SchemeError
     *         If anything goes wrong during saving (e.g. maximum sized reached).
     */
    public abstract VALUE add(final VALUE value) throws SchemeError;

    /**
     * Concrete implementation have to decide what condition must be met for a searching key and an entry key being
     * regarded as equal.
     *
     * @param key
     *         The searching key.
     * @param entryFound
     *         An entry whose key is compared to {@code key}.
     * @return True if keys match, false otherwise.
     */
    protected abstract boolean keysMatch(final KEY key, final VALUE entryFound);

}
