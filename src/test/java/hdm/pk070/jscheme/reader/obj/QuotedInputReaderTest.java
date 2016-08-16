package hdm.pk070.jscheme.reader.obj;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.reader.SchemeCharacterReader;
import hdm.pk070.jscheme.reader.SchemeReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * A test class for {@link QuotedInputReader}.
 *
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(SchemeReader.class)
public class QuotedInputReaderTest {

    private SchemeObjReader quotedInputReader;
    private SchemeCharacterReader schemeCharacterReader;

    @Before
    public void setUp() {
        String fakeInput = "'(+ a b c)";
        schemeCharacterReader = SchemeCharacterReader.withInputStream(new ByteArrayInputStream
                (fakeInput.getBytes()));
        this.quotedInputReader = QuotedInputReader.createInstance(schemeCharacterReader);
    }

    @Test
    public void testReadQuotedInput() throws SchemeError {
        SchemeCons expectedOutcome = new SchemeCons(new SchemeSymbol("quote"), new SchemeCons(new SchemeCons(new
                SchemeSymbol("+"), new SchemeCons(new SchemeSymbol("a"), new
                SchemeCons(new SchemeSymbol("b"), new SchemeCons(new SchemeSymbol("c"), new SchemeNil())))), new
                SchemeNil()));

        SchemeReader mockedReader = mock(SchemeReader.class);
        when(mockedReader.read()).thenReturn(((SchemeCons) expectedOutcome.getCdr()).getCar());

        PowerMockito.mockStatic(SchemeReader.class);
        PowerMockito.when(SchemeReader.withCurrentStream()).thenReturn(Optional.of(mockedReader));


        SchemeObject result = this.quotedInputReader.read();

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.getClass(), equalTo(SchemeCons.class));
        assertThat("Result does not match expected value!", result, equalTo(expectedOutcome));
    }
}