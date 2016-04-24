package hdm.pk070.jscheme.reader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author patrick.kleindienst
 */
public class SchemeCharacterReaderTest {

    private SchemeCharacterReader schemeCharacterReader;

    private String testInput = "foobar";
    private String inputWithSpace = " Leading space character";
    private String inputWithTab = "\tLeading tab character";
    private String inputWithNewLine = "\nLeading new line character";
    private String inputWithCarriageReturn = "\rLeading carriage return character";


    @Test
    public void testCreateInstance() {
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream
                (testInput.getBytes()));

        assertThat(schemeCharacterReader, notNullValue());
    }

    @Test(expected = NullPointerException.class)
    public void testThrowExceptionOnNullStream() {
        SchemeCharacterReader.withInputStream(null);
    }

    @Test
    public void testNextCharIs() {
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream(testInput.getBytes()));
        boolean isRightChar = schemeCharacterReader.nextCharIs(testInput.charAt(0));

        assertThat(isRightChar, equalTo(true));
    }

    @Test
    public void testNextNonWhitespaceChar() {
        assertWhitespaceIsSkipped(inputWithSpace);
        assertWhitespaceIsSkipped(inputWithTab);
        assertWhitespaceIsSkipped(inputWithNewLine);
        assertWhitespaceIsSkipped(inputWithCarriageReturn);
    }

    @After
    public void tearDown() {
        if (Objects.nonNull(schemeCharacterReader)) {
            schemeCharacterReader.shutdown();
        }
    }

    private void assertWhitespaceIsSkipped(String whitespaceString) {
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream(whitespaceString
                .getBytes()));
        char nextNonWhitespaceChar = schemeCharacterReader.nextNonWhitespaceChar();

        assertThat(nextNonWhitespaceChar, equalTo(whitespaceString.trim().charAt(0)));
    }
}
