package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.reader.obj.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;


/**
 * This class provides a convenient API for reading JScheme input from different kinds of streams. All the details are
 * handled by {@link SchemeCharacterReader}.
 *
 * @author patrick.kleindienst
 */
public class SchemeReader {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static SchemeReader schemeReader;

    private SchemeCharacterReader schemeCharacterReader;

    /**
     * Create a {@link SchemeReader} that reads from stdin.
     *
     * @return The {@link SchemeReader} singleton.
     */
    public static SchemeReader withStdin() {
        return withInputStream(System.in);
    }

    /**
     * Create a {@link SchemeReader} that reads from a certain {@link InputStream}.
     *
     * @param in
     *         The {@link InputStream} to read from.
     * @return The {@link SchemeReader} singleton.
     */
    public static SchemeReader withInputStream(InputStream in) {
        Objects.requireNonNull(in);
        if (Objects.isNull(schemeReader)) {
            schemeReader = new SchemeReader(in);
        }
        return schemeReader;
    }

    /**
     * Get access to the {@link SchemeReader} instance if present.
     *
     * @return An {@link Optional} which may contain the {@link SchemeReader} instance if it has been initialized
     * before. Otherwise the {@link Optional} will be empty.
     */
    public static Optional<SchemeReader> withCurrentStream() {
        if (Objects.nonNull(schemeReader)) {
            return Optional.of(schemeReader);
        } else {
            return Optional.empty();
        }
    }

    private SchemeReader(InputStream in) {
        Objects.requireNonNull(in);
        this.schemeCharacterReader = SchemeCharacterReader.withInputStream(in);
    }


    /**
     * Read the next {@link SchemeObject} from current {@link InputStream}.
     *
     * @return The {@link SchemeObject} which has been consumed from {@link InputStream}.
     * @throws SchemeError
     *         If anything goes wrong during reading.
     */
    public SchemeObject read() throws SchemeError {

        schemeCharacterReader.skipLeadingWhitespace();

        if (schemeCharacterReader.nextCharIs('\'')) {
            return QuotedInputReader.createInstance(schemeCharacterReader).read();
        }

        if (schemeCharacterReader.nextCharIs('(')) {
            schemeCharacterReader.skipNext();
            return ListReader.createInstance(schemeCharacterReader).read();
        }
        if (schemeCharacterReader.nextCharIs('"')) {
            return StringObjReader.createInstance(schemeCharacterReader).read();
        }
        if (schemeCharacterReader.inputIsNumber()) {
            return NumberObjReader.createInstance(schemeCharacterReader).read();
        }
        return SymbolObjReader.createInstance(schemeCharacterReader).read();
    }


    /**
     * Shutdown {@link SchemeReader} and associated {@link InputStream}.
     */
    public void shutdown() {
        LOGGER.debug("Free resources for SchemeReader instance " + this.toString());
        schemeCharacterReader.shutdown();
    }

    /**
     * Remove anything that's currently waiting at the {@link InputStream}.
     */
    public void clear() {
        schemeCharacterReader.clearInputStream();
    }

    /**
     * Give another {@link InputStream} to {@link SchemeReader}.
     *
     * @param inputStream
     *         The new {@link InputStream} to read from.
     */
    public void switchInputStream(InputStream inputStream) {
        schemeCharacterReader.shutdown();
        schemeCharacterReader = SchemeCharacterReader.withInputStream(inputStream);
    }

    /**
     * Get access to current {@link SchemeCharacterReader}.
     *
     * @return The current {@link SchemeCharacterReader} instance.
     */
    public SchemeCharacterReader getSchemeCharacterReader() {
        return schemeCharacterReader;
    }
}
