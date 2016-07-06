package hdm.pk070.jscheme.obj.builtin.function.list;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.function.list.SchemeBuiltinCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
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
 * A test class for {@link SchemeBuiltinCons}.
 *
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(SchemeCallStack.class)
public class SchemeBuiltinConsTest {


    private SchemeBuiltinFunction builtinCons;

    @Before
    public void setUp() {
        this.builtinCons = SchemeBuiltinCons.create();
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnLessThanTwoArguments() throws SchemeError {
        this.builtinCons.call(0);
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnMoreThanTwoArguments() throws SchemeError {
        this.builtinCons.call(3);
    }

    @Test
    public void testReturnConsOnValidArgumentCount() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(new SchemeInteger(42)).thenReturn(new SchemeString("foobar"));

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        SchemeObject result = this.builtinCons.call(2);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeCons.class), equalTo(true));
        assertThat("Result does not match expected value!", result, equalTo(new SchemeCons(new SchemeString("foobar")
                , new SchemeInteger(42))));
    }
}