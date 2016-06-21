package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.SchemeConstants;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.simple.SchemeString;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
public class StringObjReader extends SchemeObjReader {

    private static final Logger LOGGER = LogManager.getRootLogger();

    public static StringObjReader createInstance(SchemeCharacterReader schemeCharacterReader) {
        return new StringObjReader(schemeCharacterReader);
    }

    private StringObjReader(SchemeCharacterReader schemeCharacterReader) {
        super(schemeCharacterReader);
    }

    @Override
    public SchemeString read() throws SchemeError {
        StringBuffer stringBuffer = new StringBuffer();

        // add '"' to string buffer (beginning of string)
        appendNextChar(stringBuffer);

        while ((!schemeCharacterReader.nextCharIs('"')) && (!schemeCharacterReader
                .nextCharIs((char) SchemeConstants.EOF))) {
            if (schemeCharacterReader.nextCharIs('\\')) {
                appendWhitespaceChar(stringBuffer);
            } else {
                appendNextChar(stringBuffer);
            }
        }

        if (!schemeCharacterReader.nextCharIs((char) SchemeConstants.EOF)) {
            // add '"' to buffer (end of string)
            appendNextChar(stringBuffer);
        } else {
            throw new SchemeError("Unexpected EOF: String must end with '\"'");
        }
        return new SchemeString(stringBuffer.toString());
    }

    private void appendNextChar(StringBuffer buffer) {
        char ch;
        ch = schemeCharacterReader.nextChar();
        buffer.append(ch);
        LOGGER.debug(String.format("Added character %c (%d) to buffer", ch, (int) ch));
    }

    private void appendWhitespaceChar(StringBuffer buffer) throws SchemeError {
        char ch;
        schemeCharacterReader.skipNext();
        ch = schemeCharacterReader.nextChar();
        switch (ch) {
            case ((char) SchemeConstants.EOF):
                throw new SchemeError("Unexpected EOF");
            case 'r':
                buffer.append('\r');
                break;
            case 't':
                buffer.append('\t');
                break;
            case 'n':
                buffer.append('\n');
                break;
            default:
                break;
        }
    }
}
