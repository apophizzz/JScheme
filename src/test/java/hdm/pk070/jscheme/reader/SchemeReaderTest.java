package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeInteger;
import hdm.pk070.jscheme.util.ReflectionUtils;
import org.junit.After;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * @author patrick.kleindienst
 */
public class SchemeReaderTest {

    private SchemeReader schemeReader;


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
    public void testReadNumber() {
        assertReadNumber("1234");
    }


    @After
    public void tearDown() {
        if (Objects.nonNull(schemeReader)) {
            schemeReader.shutdown();
        }
    }


    private void assertReadNumber(String input) {
        SchemeReader schemeReader = SchemeReader.withInputStream(new ByteArrayInputStream(input.getBytes()));
        Object number = ReflectionUtils.invoke(schemeReader, "readNumber");

        assertThat("number is null!", number, notNullValue());
        assertThat("number is not of type SchemeObject!", SchemeObject.class.isAssignableFrom(number.getClass()),
                equalTo(true));
        assertThat("number is not of type SchemeInteger!", ((SchemeObject) number).typeOf(SchemeInteger.class),
                equalTo(true));
        assertThat("number does not have expected value!", ((SchemeInteger) number).getValue(), equalTo(1234));
    }

}