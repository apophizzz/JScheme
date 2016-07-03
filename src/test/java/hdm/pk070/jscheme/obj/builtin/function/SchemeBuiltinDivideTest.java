package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeFraction;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import hdm.pk070.jscheme.stack.SchemeCallStack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by patrick on 24.06.16.
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(SchemeCallStack.class)
public class SchemeBuiltinDivideTest {


    private SchemeBuiltinFunction builtinDivide;

    @Before
    public void setUp() {
        this.builtinDivide = SchemeBuiltinDivide.create();
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnZeroArguments() throws SchemeError {
        this.builtinDivide.call(0);
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorIfSingleArgIsNotANumber() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(new SchemeString("Not a number"));

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        this.builtinDivide.call(1);
    }

    @Test
    public void testReturnReciprocalOnSingleIntegerArgument() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(new SchemeInteger(2));

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        SchemeObject result = this.builtinDivide.call(1);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFraction.class), equalTo(true));
        assertThat("Result does not match expected value!", result, equalTo(new SchemeFraction(1, 2)));
    }

    @Test
    public void testReturnReciprocalOnSingleFloatArgument() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(new SchemeFloat(2.0f));

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        SchemeObject result = this.builtinDivide.call(1);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFloat.class), equalTo(true));
        assertThat("Result does not match expected value!", result, equalTo(new SchemeFloat(0.5f)));
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnInvalidArgumentOnStack() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(new SchemeString("Not a number")).thenReturn(new SchemeInteger(2))
                .thenReturn(new
                        SchemeInteger(4));

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        this.builtinDivide.call(3);
    }

    @Test
    public void testDivideMultipleValidIntegerArgumentsFromStack() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(new SchemeInteger(8)).thenReturn(new SchemeInteger(4)).thenReturn(new
                SchemeInteger(16));

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        SchemeObject result = this.builtinDivide.call(3);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeFraction.class), equalTo(true));
        assertThat("Result does not match expected value!", result, equalTo(new SchemeFraction(1, 2)));
    }
}