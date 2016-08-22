package hdm.pk070.jscheme.table.hash.impl;

import hdm.pk070.jscheme.table.hash.HashAlgProvider;

import java.util.Objects;

/**
 * This class provides an algorithm which computes a hash value from a {@link String} on the basis of the characters
 * it consists of. In order to get a reasonable hash value, the current value is multiplied by a big prime number
 * (here: 31) and XORed with the next char.
 *
 * @author patrick.kleindienst
 */
public class StandardHashAlgProvider implements HashAlgProvider {


    @Override
    public int computeHash(String symbolName) {
        Objects.requireNonNull(symbolName);
        int hash = symbolName.charAt(0);

        if (symbolName.length() > 1) {
            hash = (hash * 31) ^ symbolName.charAt(1);
            if (symbolName.length() > 2) {
                hash = (hash * 31) ^ symbolName.charAt(2);
                if (symbolName.length() > 3) {
                    hash = (hash * 31) ^ symbolName.charAt(3);
                }
            }
        }
        return hash;
    }
}

