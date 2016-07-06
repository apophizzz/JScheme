package hdm.pk070.jscheme.obj.builtin.function.list;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.function.list.SchemeBuiltinGetCdr;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

/**
 * @author patrick.kleindienst
 */

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(SchemeCallStack.class)
public class SchemeBuiltinGetCdrTest {


    private SchemeBuiltinFunction builtinCdr;

    @Before
    public void setUp() {
        this.builtinCdr = SchemeBuiltinGetCdr.create();
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnLessThanOneArgument() throws SchemeError {
        this.builtinCdr.call(0);
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnMoreThanOneArgument() throws SchemeError {
        this.builtinCdr.call(2);
    }

    @Test(expected = SchemeError.class)
    public void testThrowSchemeErrorOnNonConsArgument() throws SchemeError {
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(new SchemeString("foobar"));

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        this.builtinCdr.call(1);
    }

    @Test
    public void testReturnCdrOfConsArgument() throws SchemeError {
        SchemeCons testCons = new SchemeCons(new SchemeInteger(42), new SchemeCons(new SchemeString("foobar"), new
                SchemeCons(new SchemeFloat(99.9f), new SchemeNil())));
        SchemeCallStack mockedCallStack = mock(SchemeCallStack.class);
        when(mockedCallStack.pop()).thenReturn(testCons);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedCallStack);

        SchemeObject result = this.builtinCdr.call(1);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.typeOf(SchemeCons.class), equalTo(true));
        assertThat("Result does not match cdr of test cons!", result, equalTo(testCons.getCdr()));
    }
}