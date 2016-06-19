package hdm.pk070.jscheme.table.environment;

import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import hdm.pk070.jscheme.table.FixedSizeTable;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.Optional;

/**
 * Created by patrick on 17.06.16.
 */
public final class LocalEnvironment extends FixedSizeTable<SchemeSymbol, EnvironmentEntry> implements
        Environment<SchemeSymbol, EnvironmentEntry> {

    private static final Logger LOGGER = LogManager.getLogger(LocalEnvironment.class.getName());

    private Environment parentEnvironment;

    public static LocalEnvironment withSize(int size) {
        return new LocalEnvironment(size);
    }

    public static LocalEnvironment withSizeAndParent(int size, Environment parentEnvironment) {
        return new LocalEnvironment(size, parentEnvironment);
    }

    private LocalEnvironment(int size) {
        this(size, null);
    }

    private LocalEnvironment(int size, Environment parentEnvironment) {
        super(size);
        this.parentEnvironment = parentEnvironment;
    }

    @Override
    public Optional<EnvironmentEntry> get(final SchemeSymbol schemeSymbol) {
        LOGGER.debug(String.format("Searching for entry '%s' in %s", schemeSymbol
                .toString(), this));
        Optional<EnvironmentEntry> searchedEnvironmentEntry = super.get(schemeSymbol);

        if (!searchedEnvironmentEntry.isPresent() && Objects.nonNull(parentEnvironment)) {
            LOGGER.debug(String.format("Unable to find entry for key '%s' in %s, continue with parent", schemeSymbol
                    .toString(), this));
            searchedEnvironmentEntry = parentEnvironment.get(schemeSymbol);
        }
        if (!searchedEnvironmentEntry.isPresent()) {
            LOGGER.debug(String.format("Unable to find entry for key '%s' in %s hierarchy, return " +
                    "empty Optional", schemeSymbol
                    .toString(), this));
        } else {
            LOGGER.debug(String.format("Found entry for key '%s' in %s hierarchy, return result",
                    schemeSymbol
                            .toString(), this));
        }
        return searchedEnvironmentEntry;
    }


    @Override
    protected boolean keysMatch(final SchemeSymbol schemeSymbol, final EnvironmentEntry entryFound) {
        return schemeSymbol == entryFound.getKey();
    }

    @Override
    public String toString() {
        return String.format("Local Env [id: %d; parent -> { %s }]", this.hashCode(), this.parentEnvironment);
    }
}
