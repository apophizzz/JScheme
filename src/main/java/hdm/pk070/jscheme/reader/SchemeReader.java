package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.SchemeConstants;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.*;
import hdm.pk070.jscheme.reader.obj.IntegerObjReader;
import hdm.pk070.jscheme.reader.obj.StringObjReader;
import hdm.pk070.jscheme.symbolTable.SchemeSymbolTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Objects;


/**
 * @author patrick.kleindienst
 */
public class SchemeReader {

    private static final Logger LOGGER = LogManager.getRootLogger();


    private SchemeCharacterReader schemeCharacterReader;

    public static SchemeReader withStdin() {
        return withInputStream(System.in);
    }

    public static SchemeReader withInputStream(InputStream in) {
        Objects.requireNonNull(in);
        return new SchemeReader(in);
    }


    private SchemeReader(InputStream in) {
        Objects.requireNonNull(in);
        this.schemeCharacterReader = SchemeCharacterReader.withInputStream(in);
    }


    public SchemeObject read() throws SchemeError {

        schemeCharacterReader.skipLeadingWhitespace();

        if (schemeCharacterReader.nextCharIs('(')) {
            return readList();
        }
        if (schemeCharacterReader.nextCharIs('"')) {
            return StringObjReader.createInstance(schemeCharacterReader).read();
        }
        // TODO ISSUE: input '123abc' must be evaluated as a symbol. Right now
        // '123' is read as a number and 'abc' as a symbol.
        if (schemeCharacterReader.nextCharIsDigit()) {
            return IntegerObjReader.createInstance(schemeCharacterReader).read();
        }
        return readSymbol();
    }

    private SchemeObject readSymbol() throws SchemeError {

        StringBuffer symbolBuffer = new StringBuffer();
        char ch;

        while ((!schemeCharacterReader.nextCharIs('(')) && (!schemeCharacterReader
                .nextCharIs(')')) && (!schemeCharacterReader.nextCharIsWhiteSpace()) && (!schemeCharacterReader
                .nextCharIs((char) SchemeConstants
                        .EOF))) {
            ch = schemeCharacterReader.nextChar();
            symbolBuffer.append(ch);
            LOGGER.debug(String.format("Added character %c (%d) to symbol buffer", ch, (int) ch));
        }

        if (symbolBuffer.toString().equals(SchemeConstants.NIL_VAL)) {
            return SchemeNil.createObj();
        }

        if (symbolBuffer.toString().startsWith("#")) {
            if (symbolBuffer.toString().equals(SchemeConstants.BOOL_TRUE_VAL)) {
                return SchemeTrue.createObj();
            } else if (symbolBuffer.toString().equals(SchemeConstants.BOOL_FALSE_VAL)) {
                return SchemeFalse.createObj();
            }
        }
        return SchemeSymbolTable.getInstance().getOrAdd(symbolBuffer.toString());
    }


    private SchemeObject readList() throws SchemeError {
//        throw new UnsupportedOperationException("readList: Not implemented yet.");
        throw new SchemeError("Bla");
    }

    public void shutdown() {
        LOGGER.debug("Free resources for SchemeReader instance " + this.toString());
        schemeCharacterReader.shutdown();
    }

    public void clearReaderOnError() {
        schemeCharacterReader.clearInputStream();
    }


}
