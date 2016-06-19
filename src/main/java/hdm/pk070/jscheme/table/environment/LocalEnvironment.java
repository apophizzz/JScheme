package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import hdm.pk070.jscheme.table.FixedSizeTable;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by patrick on 17.06.16.
 */
public final class LocalEnvironment extends FixedSizeTable<SchemeSymbol, EnvironmentEntry> implements
        Environment<SchemeSymbol, EnvironmentEntry> {


    private Environment parentEnvironment;

    public static LocalEnvironment withSize(int size) {
        return new LocalEnvironment(size);
    }

    public static LocalEnvironment withSizeAndParent(int size, Environment parentEnvironment) {
        return new LocalEnvironment(size, parentEnvironment);
    }

    private LocalEnvironment(int size) {
        super(size);
    }

    private LocalEnvironment(int size, Environment parentEnvironment) {
        super(size);
        this.parentEnvironment = parentEnvironment;
    }

    @Override
    public Optional<EnvironmentEntry> get(final SchemeSymbol schemeSymbol) {
        Optional<EnvironmentEntry> searchedEnvironmentEntry = super.get(schemeSymbol);
        if (!searchedEnvironmentEntry.isPresent() && Objects.nonNull(parentEnvironment)) {
            searchedEnvironmentEntry = parentEnvironment.get(schemeSymbol);
        }
        return searchedEnvironmentEntry;
    }


    @Override
    protected boolean keysMatch(final SchemeSymbol schemeSymbol, final EnvironmentEntry entryFound) {
        return schemeSymbol == entryFound.getKey();
    }
}
