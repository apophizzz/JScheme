package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.type.SchemeString;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;
import hdm.pk070.jscheme.reader.obj.StringObjReader;
import hdm.pk070.jscheme.util.ReflectionMethodParam;
import hdm.pk070.jscheme.util.ReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 *
 */
public class StringObjReaderTest {

    private StringObjReader stringObjReader;
    private SchemeCharacterReader schemeCharacterReader;
    private String testInput;

    @Before
    public void setUp() {
        testInput = "\"This is just a simple test\"";
        schemeCharacterReader = SchemeCharacterReader.withInputStream(createFakeInputStreamFrom(testInput));
        stringObjReader = StringObjReader.createInstance(schemeCharacterReader);
    }

    @Test
    public void testRead() throws SchemeError {
        SchemeString stringObj = stringObjReader.read();

        assertThat("stringObj must not be null!", stringObj, notNullValue());
        assertThat("stringObj value does not match expected value!", stringObj.getValue(), equalTo(testInput));
    }

    @Test
    public void testAppendNextChar() {
        StringBuffer testBuffer = new StringBuffer();
        ReflectionUtils.invokeMethod(stringObjReader, "appendNextChar", new ReflectionMethodParam(StringBuffer.class,
                testBuffer));

        assertThat("testBuffer must not be empty!", testBuffer.length() > 0, equalTo(true));
        assertThat(testBuffer.toString(), equalTo(testInput.substring(0, 1)));
    }

    @Test
    public void testAppendWhitespaceChar() {
        String newLine = "\\n";
        StringBuffer newLineBuffer = writeInputToBuffer("\\n");
        assertThat(String.format("Whitespace char '%s' has not been added correctly!", newLine), newLineBuffer
                .toString(), equalTo("\n"));

        String tab = "\\t";
        StringBuffer tabBuffer = writeInputToBuffer("\\t");
        assertThat(String.format("Whitespace char '%s' has not been added correctly!", tab), tabBuffer.toString()
                , equalTo("\t"));

        String carriage = "\\r";
        StringBuffer carriageBuffer = writeInputToBuffer("\\r");
        assertThat(String.format("Whitespace char '%s' has not been added correctly!", carriage),
                carriageBuffer.toString(), equalTo("\r"));
    }

    @After
    public void tearDown() {
        if (Objects.nonNull(schemeCharacterReader)) {
            schemeCharacterReader.shutdown();
        }
    }

    private ByteArrayInputStream createFakeInputStreamFrom(String testString) {
        return new ByteArrayInputStream(testString.getBytes());
    }

    private void prepareStringObjReader(String fakeInput) {
        SchemeCharacterReader characterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream
                (fakeInput.getBytes()));
        stringObjReader.setCharacterReader(characterReader);
    }

    private StringBuffer writeInputToBuffer(String fakeInput) {
        StringBuffer testBuffer = new StringBuffer();
        prepareStringObjReader(fakeInput);
        ReflectionUtils.invokeMethod(stringObjReader, "appendWhitespaceChar", new ReflectionMethodParam(StringBuffer
                .class, testBuffer));
        return testBuffer;
    }
}
