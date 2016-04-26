package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.SchemeConstants;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeInteger;
import hdm.pk070.jscheme.obj.type.SchemeString;

import java.io.InputStream;
import java.util.Objects;

/**
 * @author patrick.kleindienst
 */
public class SchemeReader {

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


    public SchemeObject read() {
        if (schemeCharacterReader.nextNonWhitespaceCharIs('(')) {
            return readList();
        }
        if (schemeCharacterReader.nextNonWhitespaceCharIs('"')) {
            return readString();
        }
        // TODO ISSUE: input '123abc' must be evaluated as a symbol. Right now
        // '123' is read as a number and 'abc' as a symbol.
        if (schemeCharacterReader.nextNonWhitespaceCharIsDigit()) {
            return readNumber();
        }
        return readSymbol();
    }

    private SchemeObject readSymbol() {
        throw new UnsupportedOperationException("readSymbol: Not implemented yet.");
    }


    private SchemeObject readNumber() {
        int intVal = 0;
        while (schemeCharacterReader.nextCharIsDigit()) {
            intVal = intVal * 10 + (Character.getNumericValue(schemeCharacterReader.nextNonWhitespaceChar()));
        }
        return SchemeInteger.createObj(intVal);
    }

    private SchemeObject readString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(schemeCharacterReader.nextNonWhitespaceChar());

        while ((!schemeCharacterReader.nextCharIs('"')) && (!schemeCharacterReader
                .nextCharIs((char) SchemeConstants.EOF))) {
            if (schemeCharacterReader.nextCharIs('\\')) {
                schemeCharacterReader.skip();
                char ch = schemeCharacterReader.nextChar();
                switch (ch) {
                    case ((char) SchemeConstants.EOF):
                        // TODO throw error
                        break;
                    case 'r':
                        stringBuffer.append('\r');
                        break;
                    case 't':
                        stringBuffer.append('\t');
                        break;
                    case 'n':
                        stringBuffer.append('\n');
                        break;
                    default:
                        break;
                }
            } else {
                stringBuffer.append(schemeCharacterReader.nextChar());
            }
        }

        stringBuffer.append(schemeCharacterReader.nextChar());
        return SchemeString.createObj(stringBuffer.toString());
    }

    private SchemeObject readList() {
        throw new UnsupportedOperationException("readList: Not implemented yet.");
    }

    public void shutdown() {
        schemeCharacterReader.shutdown();
    }


}
