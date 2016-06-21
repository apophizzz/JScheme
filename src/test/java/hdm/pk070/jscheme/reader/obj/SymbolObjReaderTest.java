package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.simple.SchemeFalse;
import hdm.pk070.jscheme.obj.simple.SchemeNil;
import hdm.pk070.jscheme.obj.simple.SchemeSymbol;
import hdm.pk070.jscheme.obj.simple.SchemeTrue;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;
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
public class SymbolObjReaderTest {

    private SymbolObjReader symbolObjReader;
    private SchemeCharacterReader schemeCharacterReader;

    @Before
    public void setUp() {
        symbolObjReader = SymbolObjReader.createInstance(SchemeCharacterReader.withInputStream(new
                ByteArrayInputStream("".getBytes())));

    }

    @Test
    public void testNextCharIsValid() {
        assertNextCharIsValid("\n", false);
        assertNextCharIsValid("(", false);
        assertNextCharIsValid(")", false);
        assertNextCharIsValid(String.valueOf((char) -1), false);
        assertNextCharIsValid("a", true);
        assertNextCharIsValid(String.valueOf((char) 1), true);
    }

    @Test
    public void testRead() throws SchemeError {
        assertSchemeSymbol("abc", new SchemeSymbol("abc"));
        assertSchemeSymbol("nil", new SchemeNil());
        assertSchemeSymbol("#t", new SchemeTrue());
        assertSchemeSymbol("#f", new SchemeFalse());
    }

    private void assertNextCharIsValid(String testString, boolean expectedResult) {
        schemeCharacterReader = prepareCharacterReader(testString);
        symbolObjReader = SymbolObjReader.createInstance(schemeCharacterReader);
        Object nextCharIsValid = ReflectionUtils.invokeMethod(symbolObjReader, "nextCharIsValid");

        assertThat("Boolean result must not be null!", nextCharIsValid, notNullValue());
        assertThat("Boolean result does not match expected value!", nextCharIsValid, equalTo(expectedResult));
    }

    private void assertSchemeSymbol(String testInput, SchemeSymbol expectedSymbol) throws SchemeError {
        schemeCharacterReader = prepareCharacterReader(testInput);
        symbolObjReader.setCharacterReader(schemeCharacterReader);
        SchemeSymbol schemeSymbol = symbolObjReader.read();

        assertThat("schemeSymbol must not be null!", schemeSymbol, notNullValue());
        assertThat("schemeSymbol must be equal to expectedSymbol!", schemeSymbol, equalTo(expectedSymbol));
    }

    private SchemeCharacterReader prepareCharacterReader(String testString) {
        return SchemeCharacterReader.withInputStream(new ByteArrayInputStream(testString.getBytes()));
    }

    @After
    public void tearDown() {
        if (Objects.nonNull(schemeCharacterReader)) {
            schemeCharacterReader.shutdown();
        }
    }
}
