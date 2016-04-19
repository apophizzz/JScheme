package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.util.ReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by patrick on 19.04.16.
 */
public class SchemeReaderTest {

    private SchemeReader schemeReader;
    private InputStream temp;

    private String fakeInput = "Test";
    private String inputWithSpace = " Leading space character";
    private String inputWithTab = "\tLeading tab character";
    private String inputWithNewLine = "\nLeading new line character";
    private String inputWithCarriageReturn = "\rLeading carriage return character";

    @Before
    public void setUp() {
        temp = System.in;
        schemeReader = SchemeReader.withStdin();
    }

    @Test
    public void testCreateSchemeReaderInstance() {
        SchemeReader schemeReader = SchemeReader.withStdin();

        assertThat(schemeReader, notNullValue());
    }

    @Test
    public void testReadNextChar() {
        schemeReader.setStream(redefineStdin(fakeInput));

        String methodName = "readNextChar";
        Object result = ReflectionUtils.invoke(schemeReader, methodName);

        assertThat(result, notNullValue());
        assertThat(((Character) result), equalTo(fakeInput.charAt(0)));
    }

    @Test
    public void testSkipLeadingTab() {
        assertSkipWhitespace(inputWithTab);
    }

    @Test
    public void testSkipLeadingSpace() {
        assertSkipWhitespace(inputWithSpace);
    }

    @Test
    public void testSkipLeadingNewLine() {
        assertSkipWhitespace(inputWithNewLine);
    }

    @Test
    public void testSkipLeadingCarriageReturn() {
        assertSkipWhitespace(inputWithCarriageReturn);
    }


    @After
    public void tearDown() {
        schemeReader.shutdown();
        resetStdin();
    }

    private void assertSkipWhitespace(String whitespaceString) {
        schemeReader.setStream(redefineStdin(inputWithSpace));

        String methodName = "skipSpaces";
        Object result = ReflectionUtils.invoke(schemeReader, methodName);

        assertThat(result, notNullValue());
        assertThat(((Character) result), equalTo(inputWithSpace.trim
                ().charAt(0)));
    }

    private InputStream redefineStdin(String userInput) {
        Objects.nonNull(userInput);
        System.setIn(new ByteArrayInputStream(userInput.getBytes()));
        return System.in;
    }

    private void resetStdin() {
        System.setIn(temp);
    }
}