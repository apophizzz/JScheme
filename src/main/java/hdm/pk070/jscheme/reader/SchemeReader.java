package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.reader.obj.IntegerObjReader;
import hdm.pk070.jscheme.reader.obj.ListReader;
import hdm.pk070.jscheme.reader.obj.StringObjReader;
import hdm.pk070.jscheme.reader.obj.SymbolObjReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Objects;


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


    private SchemeReader(InputStream in) {
        Objects.requireNonNull(in);
        this.schemeCharacterReader = SchemeCharacterReader.withInputStream(in);
    }


    public SchemeObject read() throws SchemeError {

        schemeCharacterReader.skipLeadingWhitespace();

        if (schemeCharacterReader.nextCharIs('(')) {
            schemeCharacterReader.skipNext();
            return ListReader.createInstance(schemeCharacterReader).read();
        }
        if (schemeCharacterReader.nextCharIs('"')) {
            return StringObjReader.createInstance(schemeCharacterReader).read();
        }
        // TODO ISSUE: input '123abc' must be evaluated as a symbol. Right now
        // '123' is read as a number and 'abc' as a symbol.
        if (schemeCharacterReader.nextCharIsDigit()) {
            return IntegerObjReader.createInstance(schemeCharacterReader).read();
        }
        return SymbolObjReader.createInstance(schemeCharacterReader).read();
    }


    public void shutdown() {
        LOGGER.debug("Free resources for SchemeReader instance " + this.toString());
        schemeCharacterReader.shutdown();
    }

    public void clearReaderOnError() {
        schemeCharacterReader.clearInputStream();
    }

    public void switchInputStream(InputStream inputStream) {
        schemeCharacterReader.shutdown();
        schemeCharacterReader = SchemeCharacterReader.withInputStream(inputStream);
    }


}
