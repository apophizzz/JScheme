package hdm.pk070.jscheme.reader;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.*;
import hdm.pk070.jscheme.table.symbolTable.SchemeSymbolTable;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(SchemeSymbolTable.class)
public class SchemeReaderTest {

    private static SchemeReader schemeReader;

    private SchemeSymbolTable symbolTableMock;

    @BeforeClass
    public static void beforeClass() {
        schemeReader = SchemeReader.withInputStream(new ByteArrayInputStream("".getBytes()));
    }


    @Before
    public void setUp() throws Exception {
        SchemeSymbol testSymbol = new SchemeSymbol("abc");
        symbolTableMock = Mockito.mock(SchemeSymbolTable.class);
        Mockito.when(symbolTableMock.add(new SchemeSymbol("abc"))).thenReturn(testSymbol);
        Mockito.when(symbolTableMock.get("abc")).thenReturn(Optional.of(new SchemeSymbol("abc")));

        PowerMockito.mockStatic(SchemeSymbolTable.class);
        PowerMockito.when(SchemeSymbolTable.getInstance()).thenReturn(symbolTableMock);
    }

    @Test
    public void testSymbolTableMockIsReady() {
        assertThat(SchemeSymbolTable.getInstance(), notNullValue());
        assertThat(SchemeSymbolTable.getInstance(), equalTo(symbolTableMock));
    }

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
    public void testReadNumber() throws SchemeError {
        assertNumberInput("1234");
        assertNumberInput("   1234");
        assertNumberInput("    1234    ");
    }

    @Test
    public void testReadString() throws SchemeError {
        assertStringInput("\"This is just a test\"");
        assertStringInput("\n\"This is just a test\"");
        assertStringInput("\t\"This is just a test\"");
        assertStringInput("\r\"This is just a test\"");
        assertStringInput("       \"This is just a test\"      ");
    }


    @Test
    public void testReadSymbol() throws SchemeError {
        assertReadSymbol("nil   ", new SchemeNil());
        assertReadSymbol("   #t  ", new SchemeTrue());
        assertReadSymbol("       #f", new SchemeFalse());
        assertReadSymbol("abc", new SchemeSymbol("abc"));
    }

    @Test
    public void testReadList() throws SchemeError {
        assertReadList("   (1)  ", new SchemeInteger(1), new SchemeNil());
        assertReadList("   ( 1 )  ", new SchemeInteger(1), new SchemeNil());
        assertReadList("    (1 2  )", new SchemeInteger(1), new SchemeCons(new SchemeInteger(2), new SchemeNil()));
        assertReadList("(  1 2 3    )", new SchemeInteger(1), new SchemeCons(new SchemeInteger(2), new SchemeCons(new
                SchemeInteger(3), new SchemeNil())));
        assertReadEmptyList();
    }

    private void assertReadList(String fakeInput, SchemeObject expectedCar, SchemeObject expectedCdr) throws
            SchemeError {
        schemeReader.switchInputStream(new ByteArrayInputStream(fakeInput.getBytes()));
        SchemeObject schemeObject = schemeReader.read();

        assertThat("schemeObject must not be null!", schemeObject, notNullValue());
        assertThat("schemeObject is not of type SchemeCons!", schemeObject.typeOf(SchemeCons.class), equalTo(true));
        assertThat(((SchemeCons) schemeObject).getCar(), equalTo(expectedCar));
        assertThat(((SchemeCons) schemeObject).getCdr(), equalTo(expectedCdr));
    }

    private void assertReadEmptyList() throws SchemeError {
        schemeReader.switchInputStream(new ByteArrayInputStream("()".getBytes()));
        SchemeObject schemeObject = schemeReader.read();

        assertThat(schemeObject, notNullValue());
        assertThat(schemeObject.typeOf(SchemeNil.class), equalTo(true));
    }


    private void assertReadSymbol(String input, SchemeSymbol expectedSymbol) throws SchemeError {
        Objects.requireNonNull(expectedSymbol);
        schemeReader.switchInputStream(new ByteArrayInputStream(input.getBytes()));
        SchemeObject schemeObject = schemeReader.read();

        assertThat("symbol is null!", schemeObject, notNullValue());
        assertThat("symbol is not of type SchemeSymbol!", SchemeSymbol.class.isAssignableFrom(schemeObject.getClass()),
                equalTo(true));
        assertThat(String.format("symbol is not of type %s!", expectedSymbol.getClass().getSimpleName()),
                schemeObject.typeOf(expectedSymbol.getClass()), equalTo(true));
        assertThat("symbol does not have expected value!", schemeObject, equalTo(expectedSymbol));
    }

    @AfterClass
    public static void tearDown() {
        if (Objects.nonNull(schemeReader)) {
            schemeReader.shutdown();
        }
    }


    private void assertNumberInput(String numberInput) throws SchemeError {
        schemeReader.switchInputStream(new ByteArrayInputStream(numberInput.getBytes()));
        SchemeObject number = schemeReader.read();

        assertThat("number is null!", number, notNullValue());
        assertThat("number is not of type SchemeInteger!", number.typeOf(SchemeInteger.class),
                equalTo(true));
        assertThat("number does not have expected value!", number.getValue(), equalTo(Integer
                .valueOf(numberInput.trim())));
    }

    private void assertStringInput(String stringInput) throws SchemeError {
        schemeReader.switchInputStream(new ByteArrayInputStream(stringInput.getBytes()));
        SchemeObject schemeObject = schemeReader.read();

        assertThat(schemeObject, notNullValue());
        assertThat(schemeObject.typeOf(SchemeString.class), equalTo(true));
        assertThat(schemeObject.getValue(), equalTo(stringInput.trim()));
    }

}