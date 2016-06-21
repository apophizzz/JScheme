package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import hdm.pk070.jscheme.table.environment.GlobalEnvironment;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.simple.SchemeInteger;
import hdm.pk070.jscheme.obj.simple.SchemeSymbol;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * A JUnit4 test class for testing {@link SymbolEvaluator}.
 *
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(GlobalEnvironment.class)
public class SymbolEvaluatorTest {

    private SymbolEvaluator symbolEvaluator;
    private GlobalEnvironment globalEnvironmentMock;

    @Before
    public void setUp() {
        globalEnvironmentMock = Mockito.mock(GlobalEnvironment.class);
        Mockito.when(globalEnvironmentMock.get(new SchemeSymbol("foo"))).thenReturn(Optional.of(EnvironmentEntry
                .create(new SchemeSymbol("foo"), new SchemeInteger(42))));
        Mockito.when(globalEnvironmentMock.get(new SchemeSymbol("bar"))).thenReturn(Optional.empty());

        PowerMockito.mockStatic(GlobalEnvironment.class);
        PowerMockito.when(GlobalEnvironment.getInstance()).thenReturn(globalEnvironmentMock);

        symbolEvaluator = SymbolEvaluator.getInstance();
    }


    @Test
    public void testEvalDefinedSymbol() throws SchemeError {
        SchemeObject fooResult = symbolEvaluator.doEval(new SchemeSymbol("foo"), globalEnvironmentMock);

        assertThat("fooResult must not be null!", fooResult, notNullValue());
        assertThat(String.format("fooResult does not match expected type %s!", SchemeInteger.class.getSimpleName()),
                fooResult.typeOf(SchemeInteger.class), equalTo(true));
        assertThat(String.format("fooResult does not match expected value %d!", 42), fooResult, equalTo(new
                SchemeInteger(42)));
    }


    @Test(expected = SchemeError.class)
    public void testEvalUndefinedSymbol() throws SchemeError {
        symbolEvaluator.doEval(new SchemeSymbol("bar"), globalEnvironmentMock);
    }
}