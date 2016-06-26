package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.number.SchemeInteger;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.stack.SchemeCallStack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

/**
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(SchemeCallStack.class)
public class SchemeBuiltinTimesTest {

    private SchemeBuiltinFunction builtinTimes;
    private OngoingStubbing currentStackOngoingStubbing;

    @Before
    public void setUp() {
        this.builtinTimes = SchemeBuiltinTimes.create();
        initializeMockedCallStack(new SchemeInteger(1), new SchemeInteger(5), new
                SchemeInteger(4));
    }

    @Test
    public void testMultiplyMultipleNumbers() throws SchemeError {
        SchemeObject multiplicationResult = this.builtinTimes.call(3);

        assertMultiplicationResult(multiplicationResult, new SchemeInteger(20));
    }

    @Test
    public void testMultiplyByZeroReturnsZero() throws SchemeError {
        appendArgToMockedStackPopSequence(new SchemeInteger(0));
        SchemeObject multiplicationResult = this.builtinTimes.call(4);

        assertMultiplicationResult(multiplicationResult, new SchemeInteger(0));
    }

    @Test(expected = SchemeError.class)
    public void testThrowErrorOnInvalidArgument() throws SchemeError {
        appendArgToMockedStackPopSequence(new SchemeString("invalid argument"));
        this.builtinTimes.call(4);
    }

    private void assertMultiplicationResult(SchemeObject actualResult, SchemeInteger expectedResult) {
        assertThat("Result must not be null", actualResult, notNullValue());
        assertThat("Result must be of type SchemeInteger", actualResult.typeOf(SchemeInteger.class), equalTo
                (true));
        assertThat("Result does not match expected value", actualResult.equals(expectedResult),
                equalTo(true));
    }

    private void appendArgToMockedStackPopSequence(SchemeObject... schemeObjects) {
        for (SchemeObject schemeObject : schemeObjects) {
            currentStackOngoingStubbing = currentStackOngoingStubbing.thenReturn(schemeObject);
        }
    }

    private void initializeMockedCallStack(SchemeObject... schemeObjects) {
        SchemeCallStack callStackMock = mock(SchemeCallStack.class);
        OngoingStubbing<SchemeObject> when = when(callStackMock.pop());
        for (SchemeObject schemeObject : schemeObjects) {
            when = when.thenReturn(schemeObject);
        }
        currentStackOngoingStubbing = when;
        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(callStackMock);
    }
}