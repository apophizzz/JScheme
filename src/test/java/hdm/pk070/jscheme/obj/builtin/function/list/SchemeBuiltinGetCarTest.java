package hdm.pk070.jscheme.obj.builtin.function.list;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.function.list.SchemeBuiltinGetCar;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
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
public class SchemeBuiltinGetCarTest {

    private SchemeBuiltinFunction builtinCar;

    @Before
    public void setUp() {
        this.builtinCar = SchemeBuiltinGetCar.create();
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnLessThanOneArgument() throws SchemeError {
        this.builtinCar.call(0);
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnMoreThanOneArgument() throws SchemeError {
        this.builtinCar.call(2);
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnNonConsArgument() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(new SchemeInteger(42));

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        this.builtinCar.call(1);
    }

    @Test
    public void testReturnCarOfValidConsArgument() throws SchemeError {
        SchemeCons testCons = new SchemeCons(new SchemeInteger(42), new SchemeCons(new SchemeString
                ("foobar"), new SchemeNil()));

        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(testCons);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        SchemeObject result = this.builtinCar.call(1);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeInteger.class), equalTo(true));
        assertThat("Result does not match car of test cons!", result, equalTo(testCons.getCar()));
    }
}