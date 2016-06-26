package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeNumber;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;
import hdm.pk070.jscheme.util.ReflectionCallArg;
import hdm.pk070.jscheme.util.ReflectionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * A test class for {@link NumberObjReader}
 */
public class NumberObjReaderTest {

    private NumberObjReader numberObjReader;
    private SchemeCharacterReader schemeCharacterReader;
    private String fakeInput;

    @Before
    public void setUp() {
        fakeInput = "1234";
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream(fakeInput.getBytes()));
        numberObjReader = NumberObjReader.createInstance(schemeCharacterReader);
    }

    @Test
    public void testRead() throws SchemeError {
        SchemeNumber schemeInteger = numberObjReader.read();

        assertThat(schemeInteger, notNullValue());
        assertThat(schemeInteger.getValue(), equalTo(Integer.valueOf(fakeInput)));
    }

    @Test
    public void testNumericCharToInt() {
        Object charAsInt = ReflectionUtils.invokeMethod(numberObjReader, "numericCharToInt", new
                ReflectionCallArg(char.class, '1'));

        assertThat(charAsInt, notNullValue());
        assertThat(charAsInt.getClass(), equalTo(Integer.class));
        assertThat(charAsInt, equalTo(1));
    }

    @Test
    public void testToIntegerSchemeNumber() {
        List<Character> numericCharBuffer = new LinkedList<>();
        numericCharBuffer.add('4');
        numericCharBuffer.add('2');
        SchemeNumber result = (SchemeNumber) ReflectionUtils.invokeMethod(numberObjReader, "toSchemeNumber", new
                ReflectionCallArg(List.class, numericCharBuffer));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.getClass(), equalTo(SchemeInteger.class));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(42));
    }

    @Test
    public void testToFloatSchemeNumber() {
        List<Character> numericCharBuffer = new LinkedList<>();
        numericCharBuffer.add('4');
        numericCharBuffer.add('2');
        numericCharBuffer.add('.');
        numericCharBuffer.add('0');
        SchemeNumber result = (SchemeNumber) ReflectionUtils.invokeMethod(numberObjReader, "toSchemeNumber", new
                ReflectionCallArg(List.class, numericCharBuffer));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.getClass(), equalTo(SchemeFloat.class));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(42.0f));
    }

    @Test
    public void testToFloatSchemeNumberWithoutLeadingZero() {
        List<Character> numericCharBuffer = new LinkedList<>();
        numericCharBuffer.add('.');
        numericCharBuffer.add('5');
        SchemeNumber result = (SchemeNumber) ReflectionUtils.invokeMethod(numberObjReader, "toSchemeNumber", new
                ReflectionCallArg(List.class, numericCharBuffer));

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.getClass(), equalTo(SchemeFloat.class));
        assertThat("Result does not match expected value!", result.getValue(), equalTo(0.5f));
    }

    @After
    public void tearDown() {
        if (Objects.nonNull(schemeCharacterReader)) {
            schemeCharacterReader.shutdown();
        }
    }
}
