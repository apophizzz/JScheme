package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.reader.exception.SchemeReaderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.util.Objects;

/**
 * This class provides a high-level interface on top of the {@link PushbackReader} API. It enables its clients
 * to read characters or perform lookaheads without having to deal with any details.
 * <p>
 *
 * @author patrick.kleindienst
 */
public class SchemeCharacterReader {

    private static final Logger LOGGER = LogManager.getRootLogger();

    private PushbackReader pushbackReader;

    public static SchemeCharacterReader withInputStream(InputStream inputStream) {
        return new SchemeCharacterReader(inputStream);
    }

    private SchemeCharacterReader(InputStream inputStream) {
        Objects.requireNonNull(inputStream);
        this.pushbackReader = new PushbackReader(new InputStreamReader(inputStream));
    }

    /**
     * This method allows to perform a lookahead on the next non-whitespace character
     * without accidentally consuming it.
     *
     * @param ch The character we guess to be the next one
     * @return true, when ch equals the next char available at the stream, false otherwise
     */
    public boolean nextNonWhitespaceCharIs(char ch) {
        char nextChar = nextNonWhitespaceChar();
        unreadCharacter(nextChar);
        return nextChar == ch;
    }

    public boolean nextCharIs(char ch) {
        char nextChar = readFromPushbackReader();
        unreadCharacter(nextChar);
        return nextChar == ch;
    }

    public boolean nextNonWhitespaceCharIsDigit() {
        char nextChar = nextNonWhitespaceChar();
        unreadCharacter(nextChar);
        return Character.isDigit(nextChar);
    }

    public char nextChar() {
        char ch = readFromPushbackReader();
        LOGGER.debug(String.format("CharacterReader consumed char %c", ch));
        return ch;
    }

    /**
     * Checks if the next non-whitespace character available is a digit.
     *
     * @return true if digit, false otherwise
     */
    public boolean nextCharIsDigit() {
        char nextChar = readFromPushbackReader();
        unreadCharacter(nextChar);
        return Character.isDigit(nextChar);
    }

    public boolean nextCharIsWhiteSpace() {
        char ch = readFromPushbackReader();
        unreadCharacter(ch);
        return Character.isWhitespace(ch);
    }


    /**
     * Read next character from the {@link PushbackReader} instance.
     *
     * @return next character returned by the {@link PushbackReader}
     */
    private char readFromPushbackReader() {
        int ch;
        try {
            ch = pushbackReader.read();
        } catch (IOException e) {
            throw new SchemeReaderException("I/O Exception occurred reading from input stream", e);
        }
        return (char) ch;
    }

    /**
     * Takes a character and 'unreads' it by means of the {@link PushbackReader}. This way,
     * the character ch is returned when {@link SchemeCharacterReader#readFromPushbackReader}
     * is called next time.
     *
     * @param ch the character to 'unread'
     */
    private void unreadCharacter(char ch) {
        try {
            pushbackReader.unread(ch);
        } catch (IOException e) {
            throw new SchemeReaderException("Buffer full or some other I/O issue occurred", e);
        }
    }

    /**
     * Read the next non-whitespace character from the input stream.
     *
     * @return the next non-whitespace character available
     */
    public char nextNonWhitespaceChar() {
        char ch;
        do {
            ch = readFromPushbackReader();
        } while (Character.isWhitespace(ch));
        return ch;
    }

    public void skipLeadingWhitespace() {
        char ch;
        do {
            ch = readFromPushbackReader();
        } while (Character.isWhitespace(ch));
        unreadCharacter(ch);
    }

    /**
     *
     */
    public void skipNext() {
        readFromPushbackReader();
    }

    /**
     *
     */
    public void clearInputStream() {
        do {
            readFromPushbackReader();
        } while (isPushbackReaderReady());
    }

    private boolean isPushbackReaderReady() {
        boolean ready = false;
        try {
            ready = pushbackReader.ready();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ready;
    }

    /**
     * Free the {@link PushbackReader} instance and all associated resources
     */
    public void shutdown() {
        try {
            pushbackReader.close();
        } catch (IOException e) {
            throw new SchemeReaderException("An exception occurred while closing the PushbackInputStream", e);
        }
    }
}
