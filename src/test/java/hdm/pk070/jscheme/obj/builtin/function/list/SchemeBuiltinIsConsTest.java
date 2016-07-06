package hdm.pk070.jscheme.obj.builtin.function.list;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.function.list.SchemeBuiltinIsCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeBool;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeFalse;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeTrue;
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
 * A test class for {@link SchemeBuiltinIsCons}.
 *
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(SchemeCallStack.class)
public class SchemeBuiltinIsConsTest {

    private SchemeBuiltinFunction builtinIsCons;

    @Before
    public void setUp() {
        this.builtinIsCons = SchemeBuiltinIsCons.create();
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnZeroArguments() throws SchemeError {
        this.builtinIsCons.call(0);
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnMoreThanOneArgument() throws SchemeError {
        this.builtinIsCons.call(2);
    }

    @Test
    public void testReturnFalseOnNonConsArgument() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(new SchemeInteger(42));

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        SchemeObject result = this.builtinIsCons.call(1);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.subtypeOf(SchemeBool.class), equalTo(true));
        assertThat("Result does not match expected value!", result, equalTo(new SchemeFalse()));
    }

    @Test
    public void testReturnTrueOnConsArgument() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(new SchemeCons(new SchemeInteger(42), new SchemeCons(new SchemeString
                ("foobar"), new SchemeNil())));

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        SchemeObject result = this.builtinIsCons.call(1);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.subtypeOf(SchemeBool.class), equalTo(true));
        assertThat("Result does not match expected value!", result, equalTo(new SchemeTrue()));
    }
}