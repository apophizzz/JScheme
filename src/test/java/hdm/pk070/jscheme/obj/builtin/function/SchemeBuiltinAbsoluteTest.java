package hdm.pk070.jscheme.obj.builtin.function;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.stack.SchemeCallStack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(SchemeCallStack.class)
public class SchemeBuiltinAbsoluteTest {

    private SchemeBuiltinFunction builtinAbs;

    @Before
    public void setUp() {
        this.builtinAbs = SchemeBuiltinAbsolute.create();
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnZeroArguments() throws SchemeError {
        this.builtinAbs.call(0);
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnMoreThanOneArgument() throws SchemeError {
        this.builtinAbs.call(2);
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorIfArgumentIsNotANumber() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(new SchemeString("Not a number"));

        PowerMockito.mockStatic(SchemeCallStack.class);
        when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        this.builtinAbs.call(1);
    }

    @Test
    public void testReturnCorrectResultOnValidArg() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(new SchemeInteger(-42));

        PowerMockito.mockStatic(SchemeCallStack.class);
        when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        SchemeObject result = this.builtinAbs.call(1);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeInteger.class), equalTo(true));
        assertThat("Result does not match expected value!", result, equalTo(new SchemeInteger(42)));
    }
}