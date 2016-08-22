package hdm.pk070.jscheme.table.hash;

/**
 * A general interface for setting up an abstraction layer for different kinds of hashing algorithms.
 *
 * @author patrick.kleindienst
 */
public interface HashAlgProvider {

    /**
     * Taking a {@link String} and computing a hash.
     *
     * @param symbolName
     *         The {@link String} that shall be used for hash computation.
     * @return The computed hash.
     */
    int computeHash(String symbolName);
}
