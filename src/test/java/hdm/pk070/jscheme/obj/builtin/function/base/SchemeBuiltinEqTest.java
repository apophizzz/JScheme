package hdm.pk070.jscheme.obj.builtin.function.base;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.function.SchemeBuiltinFunction;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeFalse;
import hdm.pk070.jscheme.obj.builtin.simple.bool.SchemeTrue;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeFraction;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.builtin.simple.number.floatComplex.SchemeFloat;
import hdm.pk070.jscheme.stack.SchemeCallStack;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
@PrepareForTest({SchemeCallStack.class})
public class SchemeBuiltinEqTest {


    private SchemeBuiltinFunction builtinEq;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.builtinEq = SchemeBuiltinEq.create();
    }

    @Test
    public void testCallThrowsErrorOnZeroArguments() throws SchemeError {
        expectedException.expect(SchemeError.class);
        expectedException.expectMessage("(eq?): arity mismatch, expected number of arguments does not match the given" +
                " number [expected: 2, given: 0]");

        this.builtinEq.call(0);
    }

    @Test
    public void testCallThrowsErrorOnSingleArgument() throws SchemeError {
        expectedException.expect(SchemeError.class);
        expectedException.expectMessage("(eq?): arity mismatch, expected number of arguments does not match the given" +
                " number [expected: 2, given: 1]");

        this.builtinEq.call(1);
    }

    @Test
    public void testCallThrowsErrorOnMoreThanTwoArguments() throws SchemeError {
        expectedException.expect(SchemeError.class);
        expectedException.expectMessage("(eq?): arity mismatch, expected number of arguments does not match the given" +
                " number [expected: 2, given: 3]");

        this.builtinEq.call(3);
    }

    @Test
    public void testCallReturnsTrueOnSameReferenceComparison() throws SchemeError {
        SchemeCons testList = new SchemeCons(new SchemeInteger(1), new SchemeCons(new SchemeInteger(2), new
                SchemeNil()));

        SchemeCallStack mockedStack = mock(SchemeCallStack.class);
        when(mockedStack.pop()).thenReturn(testList).thenReturn(testList);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedStack);

        SchemeObject result = this.builtinEq.call(2);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected value!", result, equalTo(new SchemeTrue()));
    }

    @Test
    public void testCallReturnsFalseOnDifferentReferenceComparison() throws SchemeError {
        SchemeCons firstList = new SchemeCons(new SchemeInteger(1), new SchemeCons(new SchemeInteger(2), new
                SchemeNil()));
        SchemeCons secondList = new SchemeCons(new SchemeInteger(1), new SchemeCons(new SchemeInteger(2), new
                SchemeNil()));

        SchemeCallStack mockedStack = mock(SchemeCallStack.class);
        when(mockedStack.pop()).thenReturn(secondList).thenReturn(firstList);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedStack);

        SchemeObject result = this.builtinEq.call(2);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected value!", result, equalTo(new SchemeFalse()));
    }

    @Test
    public void testCallReturnsTrueOnSameFloatValues() throws SchemeError {
        SchemeFloat firstFloat = new SchemeFloat(42.0f);
        SchemeFloat secondFloat = new SchemeFloat(42.0f);

        SchemeCallStack mockedStack = mock(SchemeCallStack.class);
        when(mockedStack.pop()).thenReturn(secondFloat).thenReturn(firstFloat);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedStack);

        SchemeObject result = this.builtinEq.call(2);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected value!", result, equalTo(new SchemeTrue()));
    }

    @Test
    public void testCallReturnsFalseOnDifferentFloatValues() throws SchemeError {
        SchemeFloat firstFloat = new SchemeFloat(42.0f);
        SchemeFloat secondFloat = new SchemeFloat(43.0f);

        SchemeCallStack mockedStack = mock(SchemeCallStack.class);
        when(mockedStack.pop()).thenReturn(secondFloat).thenReturn(firstFloat);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedStack);

        SchemeObject result = this.builtinEq.call(2);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected value!", result, equalTo(new SchemeFalse()));
    }

    @Test
    public void testCallReturnsTrueOnSameIntegerValues() throws SchemeError {
        SchemeInteger firstInteger = new SchemeInteger(42);
        SchemeInteger secondInteger = new SchemeInteger(42);

        SchemeCallStack mockedStack = mock(SchemeCallStack.class);
        when(mockedStack.pop()).thenReturn(secondInteger).thenReturn(firstInteger);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedStack);

        SchemeObject result = this.builtinEq.call(2);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected value!", result, equalTo(new SchemeTrue()));
    }

    @Test
    public void testCallReturnsFalseOnDifferentIntegerValue() throws SchemeError {
        SchemeInteger firstInteger = new SchemeInteger(42);
        SchemeInteger secondInteger = new SchemeInteger(43);

        SchemeCallStack mockedStack = mock(SchemeCallStack.class);
        when(mockedStack.pop()).thenReturn(secondInteger).thenReturn(firstInteger);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedStack);

        SchemeObject result = this.builtinEq.call(2);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected value!", result, equalTo(new SchemeFalse()));
    }

    @Test
    public void testCallReturnsTrueOnSameFractionValues() throws SchemeError {
        SchemeFraction firstFraction = new SchemeFraction(1, 2);
        SchemeFraction secondFraction = new SchemeFraction(1, 2);

        SchemeCallStack mockedStack = mock(SchemeCallStack.class);
        when(mockedStack.pop()).thenReturn(secondFraction).thenReturn(firstFraction);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedStack);

        SchemeObject result = this.builtinEq.call(2);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected value!", result, equalTo(new SchemeTrue()));
    }

    @Test
    public void testCallReturnsFalseOnDifferentFractionValues() throws SchemeError {
        SchemeFraction firstFraction = new SchemeFraction(1, 2);
        SchemeFraction secondFraction = new SchemeFraction(1, 4);

        SchemeCallStack mockedStack = mock(SchemeCallStack.class);
        when(mockedStack.pop()).thenReturn(secondFraction).thenReturn(firstFraction);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedStack);

        SchemeObject result = this.builtinEq.call(2);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected value!", result, equalTo(new SchemeFalse()));
    }

    @Test
    public void testCallReturnsTrueOnEqualStrings() throws SchemeError {
        SchemeString firstString = new SchemeString("This is a string");
        SchemeString secondString = new SchemeString("This is a string");

        SchemeCallStack mockedStack = mock(SchemeCallStack.class);
        when(mockedStack.pop()).thenReturn(secondString).thenReturn(firstString);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedStack);

        SchemeObject result = this.builtinEq.call(2);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected value!", result, equalTo(new SchemeTrue()));
    }

    @Test
    public void testCallReturnsFalseOnDifferentStrings() throws SchemeError {
        SchemeString firstString = new SchemeString("This is a string");
        SchemeString secondString = new SchemeString("This is another string");

        SchemeCallStack mockedStack = mock(SchemeCallStack.class);
        when(mockedStack.pop()).thenReturn(secondString).thenReturn(firstString);

        PowerMockito.mockStatic(SchemeCallStack.class);
        PowerMockito.when(SchemeCallStack.instance()).thenReturn(mockedStack);

        SchemeObject result = this.builtinEq.call(2);

        assertThat("Result must not be null!", result, notNullValue());
        assertThat("Result does not match expected value!", result, equalTo(new SchemeFalse()));
    }
}