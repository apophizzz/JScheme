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

    private String fakeInput = "Test";
    private String inputWithSpace = " Leading space character";
    private String inputWithTab = "\tLeading tab character";
    private String inputWithNewLine = "\nLeading new line character";
    private String inputWithCarriageReturn = "\rLeading carriage return character";


    @Test
    public void testCreateSchemeReaderInstance() {
        SchemeReader schemeReaderStdIn = SchemeReader.withStdin();
        SchemeReader schemeReaderWithStream = SchemeReader.withInputStream(System.in);

        assertThat(schemeReaderStdIn, notNullValue());
        assertThat(schemeReaderWithStream, notNullValue());
    }

    @Test(expected = NullPointerException.class)
    public void testPassNullToCreationMethodThrowsException() {
        SchemeReader.withInputStream(null);
    }

    @Test
    public void testReadNextChar() {
        schemeReader = SchemeReader.withInputStream(createDummyInputStream(fakeInput));
        String methodName = "readNextChar";
        Object result = ReflectionUtils.invoke(schemeReader, methodName);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(fakeInput.charAt(0)));
    }

    @Test
    public void testSkipSpaces() {
        assertSkipWhitespace(inputWithSpace);
        assertSkipWhitespace(inputWithTab);
        assertSkipWhitespace(inputWithNewLine);
        assertSkipWhitespace(inputWithCarriageReturn);
    }


    @After
    public void tearDown() {
        if (Objects.nonNull(schemeReader)) {
            schemeReader.shutdown();
        }
    }

    private void assertSkipWhitespace(String whitespaceString) {
        schemeReader = SchemeReader.withInputStream(createDummyInputStream(whitespaceString));
        String methodName = "skipSpaces";
        Object result = ReflectionUtils.invoke(schemeReader, methodName);

        assertThat(result, notNullValue());
        assertThat(result, equalTo(inputWithSpace.trim().charAt(0)));
    }

    private InputStream createDummyInputStream(String userInput) {
        Objects.nonNull(userInput);
        return new ByteArrayInputStream(userInput.getBytes());
    }

}