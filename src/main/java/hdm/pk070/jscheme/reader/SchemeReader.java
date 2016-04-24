package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.reader.exception.SchemeReaderException;

import java.io.*;
import java.util.Objects;

/**
 * @author patrick.kleindienst
 */
public class SchemeReader {


    private BufferedReader bufferedReader;

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
        // ignore leading whitespace until a character is met
//        char ch = this.skipSpaces();
//        System.out.println(ch);
        schemeCharacterReader.nextNonWhitespaceChar();
        return null;
    }

    public void shutdown() {
        schemeCharacterReader.shutdown();
    }

    public void setStream(InputStream inputStream) {
        this.schemeCharacterReader = SchemeCharacterReader.withInputStream(inputStream);
    }


}
