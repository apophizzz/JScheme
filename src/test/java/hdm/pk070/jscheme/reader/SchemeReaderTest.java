package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.*;
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

    private static final String METHOD_READ_NUMBER = "readNumber";
    private static final String METHOD_READ_STRING = "readString";
    private static final String METHOD_READ_SYMBOL = "readSymbol";

    private SchemeReader schemeReader;


    @Test
    public void testCreateSchemeReaderInstance() {
        schemeReader = SchemeReader.withStdin();
        assertThat(schemeReader, notNullValue());

        schemeReader = SchemeReader.withInputStream(System.in);
        assertThat(schemeReader, notNullValue());
    }

    @Test(expected = NullPointerException.class)
    public void testPassNullToCreationMethodThrowsException() {
        SchemeReader.withInputStream(null);
    }


    @Test
    public void testReadNumber() {
        String testInput = "1234";
        schemeReader = SchemeReader.withInputStream(new ByteArrayInputStream(testInput.getBytes()));
        Object number = ReflectionUtils.invoke(schemeReader, METHOD_READ_NUMBER);

        assertThat("number is null!", number, notNullValue());
        assertThat("number is not of type SchemeObject!", SchemeObject.class.isAssignableFrom(number.getClass()),
                equalTo(true));
        assertThat("number is not of type SchemeInteger!", ((SchemeObject) number).typeOf(SchemeInteger.class),
                equalTo(true));
        assertThat("number does not have expected value!", ((SchemeInteger) number).getValue(), equalTo(Integer
                .valueOf(testInput)));
    }

    @Test
    public void testReadString() {
        String testInput = "\"This is just a test\"";
        schemeReader = SchemeReader.withInputStream(new ByteArrayInputStream(testInput.getBytes()));
        Object string = ReflectionUtils.invoke(schemeReader, METHOD_READ_STRING);

        assertThat("string is null!", string, notNullValue());
        assertThat("string is not of type SchemeObject!", SchemeObject.class.isAssignableFrom(string.getClass()),
                equalTo(true));
        assertThat("string is not of type SchemeString!", ((SchemeObject) string).typeOf(SchemeString.class), equalTo
                (true));
        assertThat("string does not have expected value!", ((SchemeString) string).getValue(), equalTo(testInput));
    }


    @Test
    public void testReadSymbol() {
        assertReadSymbol("nil", new SchemeNil());
        assertReadSymbol("#t", new SchemeTrue());
        assertReadSymbol("#f", new SchemeFalse());
    }


    private <T extends SchemeSymbol> void assertReadSymbol(String input, SchemeSymbol expectedSymbol) {
        Objects.requireNonNull(expectedSymbol);
        schemeReader = SchemeReader.withInputStream(new ByteArrayInputStream(input.getBytes()));
        Object symbol = ReflectionUtils.invoke(schemeReader, METHOD_READ_SYMBOL);

        assertThat("symbol is null!", symbol, notNullValue());
        assertThat("symbol is not of type SchemeObject!", SchemeObject.class.isAssignableFrom(symbol.getClass()),
                equalTo(true));
        assertThat("symbol is not of type SchemeSymbol!", SchemeSymbol.class.isAssignableFrom(symbol.getClass()),
                equalTo(true));
        assertThat(String.format("symbol is not of type %s!", expectedSymbol.getClass().getSimpleName()), (
                (SchemeSymbol) symbol).typeOf(expectedSymbol.getClass()), equalTo(true));
        assertThat("symbol does not have expected value!", symbol, equalTo(expectedSymbol));
    }

    @After
    public void tearDown() {
        if (Objects.nonNull(schemeReader)) {
            schemeReader.shutdown();
        }
    }


}