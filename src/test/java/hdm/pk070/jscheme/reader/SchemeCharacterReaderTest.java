package hdm.pk070.jscheme.reader;

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

    private String testInput = "\t\nfoobar";
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
    public void testInputIsNumber() {
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream("12345".getBytes()));
        boolean isNumber = schemeCharacterReader.inputIsNumber();
        assertThat(isNumber, equalTo(true));

        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream("12345\n".getBytes()));
        isNumber = schemeCharacterReader.inputIsNumber();
        assertThat(isNumber, equalTo(true));
    }

    @Test
    public void testInputIsNotNumber() {
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream("123%bc".getBytes()));
        boolean isNumber = schemeCharacterReader.inputIsNumber();
        assertThat(isNumber, equalTo(false));

        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream("123#bc".getBytes()));
        isNumber = schemeCharacterReader.inputIsNumber();
        assertThat(isNumber, equalTo(false));
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
