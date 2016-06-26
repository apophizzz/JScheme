package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeInteger;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;
import hdm.pk070.jscheme.util.ReflectionCallArg;
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
public class IntegerObjReaderTest {

    private IntegerObjReader integerObjReader;
    private SchemeCharacterReader schemeCharacterReader;
    private String fakeInput;

    @Before
    public void setUp() {
        fakeInput = "1234";
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream(fakeInput.getBytes()));
        integerObjReader = IntegerObjReader.createInstance(schemeCharacterReader);
    }

    @Test
    public void testRead() throws SchemeError {
        SchemeInteger schemeInteger = integerObjReader.read();

        assertThat(schemeInteger, notNullValue());
        assertThat(schemeInteger.getValue(), equalTo(Integer.valueOf(fakeInput)));
    }

    @Test
    public void testNumericCharToInt() {
        Object charAsInt = ReflectionUtils.invokeMethod(integerObjReader, "numericCharToInt", new
                ReflectionCallArg(char.class, '1'));

        assertThat(charAsInt, notNullValue());
        assertThat(charAsInt.getClass(), equalTo(Integer.class));
        assertThat(charAsInt, equalTo(1));
    }

    @After
    public void tearDown() {
        if (Objects.nonNull(schemeCharacterReader)) {
            schemeCharacterReader.shutdown();
        }
    }
}
