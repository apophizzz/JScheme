package hdm.pk070.jscheme.table.hash.impl;

import hdm.pk070.jscheme.table.hash.HashAlgProvider;

import java.util.Objects;

/**
 *
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

