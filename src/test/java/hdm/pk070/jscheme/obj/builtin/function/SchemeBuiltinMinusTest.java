package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
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

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * A test class for {@link SchemeBuiltinMinus}
 *
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(SchemeCallStack.class)
public class SchemeBuiltinMinusTest {

    private SchemeBuiltinFunction builtinMinus;

    @Before
    public void setUp() {
        this.builtinMinus = SchemeBuiltinMinus.create();
    }

    @Test(expected = SchemeError.class)
    public void testThrowErrorOnZeroArgCount() throws SchemeError {
        builtinMinus.call(0);
    }

    @Test(expected = SchemeError.class)
    public void testThrowErrorIfSingleArgIsNotANumber() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        appendPopValues(mockedCallStack, new SchemeString("String, no number"));

        defineSingletonMockInstance(mockedCallStack);

        builtinMinus.call(1);
    }

    @Test
    public void testReturnInverseOnSingleArg() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        appendPopValues(mockedCallStack, new SchemeInteger(42));

        defineSingletonMockInstance(mockedCallStack);

        SchemeObject subtractionResult = builtinMinus.call(1);

        assertThat("Result must not be null", subtractionResult, notNullValue());
        assertThat("Result must be of type SchemeInteger", subtractionResult.typeOf(SchemeInteger.class), equalTo
                (true));
        assertThat("Result does not match expected value", subtractionResult.getValue().equals(-42), equalTo(true));
    }

    @Test(expected = SchemeError.class)
    public void testThrowErrorIfFirstArgIsNotANumber() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        appendPopValues(mockedCallStack, new SchemeString("Not a number"));

        defineSingletonMockInstance(mockedCallStack);

        builtinMinus.call(10);
    }

    @Test(expected = SchemeError.class)
    public void testThrowErrorIfAnyArgIsNotANumber() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        appendPopValues(mockedCallStack, new SchemeInteger(20), new SchemeInteger(10), new SchemeString("Not" +
                " a number"));

        defineSingletonMockInstance(mockedCallStack);

        builtinMinus.call(3);
    }

    @Test
    public void testSubtractionWithValidStack() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        appendPopValues(mockedCallStack, new SchemeInteger(5), new SchemeInteger
                (10), new SchemeInteger(20));
        appendIsEmptyValues(mockedCallStack, false, false, true);

        defineSingletonMockInstance(mockedCallStack);

        SchemeObject subtractionResult = builtinMinus.call(3);

        assertThat("Result must not be null", subtractionResult, notNullValue());
        assertThat("Result must be of type SchemeInteger", subtractionResult.typeOf(SchemeInteger.class), equalTo
                (true));
        assertThat("Result does not match expected value", subtractionResult.getValue().equals(5), equalTo(true));
    }


    private void appendIsEmptyValues(SchemeCallStack callStackMock, Boolean... bools) {
        OngoingStubbing<Boolean> when = when(callStackMock.isEmpty());
        for (Boolean bool : bools) {
            when = when.thenReturn(bool);
        }
    }

    private void appendPopValues(SchemeCallStack callStackMock, SchemeObject... schemeObjects) {
        OngoingStubbing<SchemeObject> when = when(callStackMock.pop());
        for (SchemeObject schemeObject : schemeObjects) {
            when = when.thenReturn(schemeObject);
        }

    }

    private void defineSingletonMockInstance(SchemeCallStack callStackMock) {
        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(callStackMock);
    }
}