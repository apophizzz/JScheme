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
    private static int instanceCount = 0;

    private Environment parentEnvironment;
    private int identifier;

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
        this.identifier = instanceCount + 1;
        instanceCount++;
    }

    @Override
    public Optional<EnvironmentEntry> get(final SchemeSymbol schemeSymbol) {
        LOGGER.debug(String.format("Searching for entry '%s' in local env [id: %d] -> parent: [%d]", schemeSymbol
                .toString(), this.identifier, this.getParentIdIfExists()));
        Optional<EnvironmentEntry> searchedEnvironmentEntry = super.get(schemeSymbol);

        if (!searchedEnvironmentEntry.isPresent() && Objects.nonNull(parentEnvironment)) {
            LOGGER.debug(String.format("Unable to find entry for key '%s' in local env [id: %d], continue with parent" +
                    " [id: %d]", schemeSymbol
                    .toString(), this.identifier, this.getParentIdIfExists()));
            searchedEnvironmentEntry = parentEnvironment.get(schemeSymbol);
        }
        if (!searchedEnvironmentEntry.isPresent()) {
            LOGGER.debug(String.format("Unable to find entry for key '%s' in local env [id: %d] hierarchy, return " +
                    "empty Optional", schemeSymbol
                    .toString(), this.identifier));
        } else {
            LOGGER.debug(String.format("Found entry for key '%s' in local env [id: %d] hierarchy, return result",
                    schemeSymbol
                    .toString(), this.identifier));
        }
        return searchedEnvironmentEntry;
    }

    private int getParentIdIfExists() {
        if (Objects.nonNull(parentEnvironment)) {
            return parentEnvironment.getIdentifier();
        } else {
            return -1;
        }
    }


    @Override
    protected boolean keysMatch(final SchemeSymbol schemeSymbol, final EnvironmentEntry entryFound) {
        return schemeSymbol == entryFound.getKey();
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    protected void finalize() throws Throwable {
        instanceCount--;
        super.finalize();
    }
}
