package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.reader.exception.SchemeReaderException;

import java.io.*;
import java.util.Objects;

/**
 * Created by patrick on 19.04.16.
 */
public class SchemeReader {


    private static SchemeReader schemeReader;

    private BufferedReader bufferedReader;

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
        this.bufferedReader = new BufferedReader(new InputStreamReader(in));
    }


    public SchemeObject read() {
        // ignore leading whitespace until a character is met
        char ch = this.skipSpaces();
        return null;
    }

    public void shutdown() {
        try {
            this.bufferedReader.close();
        } catch (IOException e) {
            throw new SchemeReaderException("Could not close SchemeReader input stream", e);
        }
    }

    public void setStream(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    private Character skipSpaces() {
        char ch;
        do {
            ch = readNextChar();
        } while (Character.isWhitespace(ch));
        return ch;
    }

    private char readNextChar() {
        try {
            return (char) this.bufferedReader.read();
        } catch (IOException e) {
            shutdown();
            throw new SchemeReaderException("Exception occurred trying to read next character from input stream", e);
        }
    }


}
