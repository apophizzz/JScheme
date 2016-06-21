package hdm.pk070.jscheme.table.hash.impl;

import hdm.pk070.jscheme.table.hash.HashAlgProvider;
import hdm.pk070.jscheme.util.ReflectionCallArg;
import hdm.pk070.jscheme.util.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class StandardHashAlgProviderTest {

    private HashAlgProvider hashAlgProvider;

    @Before
    public void setUp() {
        hashAlgProvider = new StandardHashAlgProvider();
    }

    @Test
    public void testComputeHash() {
        String testStringA = "Foobar";
        String testStringB = "Fizzbuzz";

        Object hashA = doHashing(testStringA);
        Object hashB = doHashing(testStringB);
        Object hashC = doHashing(testStringA);

        assertThat(hashA, notNullValue());
        assertThat(hashB, notNullValue());
        assertThat(hashC, notNullValue());
        assertThat(hashA, equalTo(hashC));
        assertThat(hashA.equals(hashB), equalTo(false));
    }

    private Object doHashing(String input) {
        return ReflectionUtils.invokeMethod(hashAlgProvider, "computeHash", new ReflectionCallArg(String.class, input));
    }
}
