package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.error.SchemeError;
import org.junit.After;
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

    private final String testInput = "\t\nfoobar";
    private final String inputWithSpace = " Leading space character";
    private final String inputWithTab = "\tLeading tab character";
    private final String inputWithNewLine = "\nLeading new line character";
    private final String inputWithCarriageReturn = "\rLeading carriage return character";


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
    public void testNextNonWhitespaceCharIs() {
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream(testInput.getBytes()));
        boolean isRightChar = schemeCharacterReader.nextNonWhitespaceCharIs(testInput.trim().charAt(0));

        assertThat(isRightChar, equalTo(true));
    }

    @Test
    public void testNextNonWhitespaceChar() {
        assertWhitespaceIsSkipped(inputWithSpace);
        assertWhitespaceIsSkipped(inputWithTab);
        assertWhitespaceIsSkipped(inputWithNewLine);
        assertWhitespaceIsSkipped(inputWithCarriageReturn);
    }

    @Test
    public void testInputIsNumber() throws SchemeError {
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream("12345".getBytes()));
        boolean isNumber = schemeCharacterReader.inputIsNumber();
        assertThat(isNumber, equalTo(true));

        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream("12345\n".getBytes()));
        isNumber = schemeCharacterReader.inputIsNumber();
        assertThat(isNumber, equalTo(true));
    }

    @Test
    public void testInputIsNotNumber() throws SchemeError {
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream("123%bc".getBytes()));
        boolean isNumber = schemeCharacterReader.inputIsNumber();
        assertThat(isNumber, equalTo(false));

        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream("123#bc".getBytes()));
        isNumber = schemeCharacterReader.inputIsNumber();
        assertThat(isNumber, equalTo(false));
    }

    @Test
    public void testValidFloatInputIsNumber() throws SchemeError {
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream("42.4242".getBytes()));
        boolean isNumber = schemeCharacterReader.inputIsNumber();

        assertThat(isNumber, equalTo(true));
    }

    @Test
    public void testInvalidFloatInputIsNotNumber() throws SchemeError {
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream("42.foobar".getBytes()));
        boolean isNumber = schemeCharacterReader.inputIsNumber();

        assertThat(isNumber, equalTo(false));
    }

    @Test
    public void testReadPlusPrefixedNumberCorrectly() throws SchemeError {
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream("+42.0".getBytes()));
        boolean isNumber = schemeCharacterReader.inputIsNumber();

        assertThat(isNumber, equalTo(true));
    }

    @Test
    public void testReadMinusPrefixedNumberCorrectly() throws SchemeError {
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream("-42.0".getBytes()));
        boolean isNumber = schemeCharacterReader.inputIsNumber();

        assertThat(isNumber, equalTo(true));
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
