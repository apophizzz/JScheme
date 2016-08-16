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
 * @author patrick.kleindienst
 */
public class SchemeReader {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private static SchemeReader schemeReader;

    private SchemeCharacterReader schemeCharacterReader;

    public static SchemeReader withStdin() {
        return withInputStream(System.in);
    }

    public static SchemeReader withInputStream(InputStream in) {
        Objects.requireNonNull(in);
        if (Objects.isNull(schemeReader)) {
            schemeReader = new SchemeReader(in);
        }
        return schemeReader;
    }

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


    public void shutdown() {
        LOGGER.debug("Free resources for SchemeReader instance " + this.toString());
        schemeCharacterReader.shutdown();
    }

    public void clear() {
        schemeCharacterReader.clearInputStream();
    }

    public void switchInputStream(InputStream inputStream) {
        schemeCharacterReader.shutdown();
        schemeCharacterReader = SchemeCharacterReader.withInputStream(inputStream);
    }

    public SchemeCharacterReader getSchemeCharacterReader() {
        return schemeCharacterReader;
    }
}
