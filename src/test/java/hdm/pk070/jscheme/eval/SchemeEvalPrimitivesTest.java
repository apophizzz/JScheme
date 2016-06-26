package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.number.exact.SchemeInteger;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.LocalEnvironment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * A test class for {@link SchemeEval}, focusing on evaluating Scheme primitive types
 *
 * @author patrick.kleindienst
 */
public class SchemeEvalPrimitivesTest {

    private SchemeEval schemeEval;
    private Environment<SchemeSymbol, EnvironmentEntry> dummyEnvironment;

    @Before
    public void setUp() {
        schemeEval = SchemeEval.getInstance();
        dummyEnvironment = LocalEnvironment.withSize(42);
    }

    @Test
    public void testEvalNumber() throws SchemeError {
        SchemeObject evalResult = schemeEval.eval(new SchemeInteger(1), dummyEnvironment);

        assertThat("evalResult must not be null!", evalResult, notNullValue());
        assertThat(String.format("evalResult does not match expected type %s!", SchemeInteger.class.getSimpleName()),
                evalResult.typeOf(SchemeInteger.class), equalTo
                        (true));
        assertThat(String.format("evalResult does not match expected value %d!", 1), evalResult, equalTo(new
                SchemeInteger(1)));
    }

    @Test
    public void testEvalString() throws SchemeError {
        SchemeObject evalResult = schemeEval.eval(new SchemeString("foobar"), dummyEnvironment);

        assertThat("evalResult must not be null!", evalResult, notNullValue());
        assertThat(String.format("evalResult does not match expected type %s!", SchemeString.class.getSimpleName()),
                evalResult.typeOf(SchemeString.class), equalTo(true));
        assertThat(String.format("evalResult does not match expected value %s!", "foobar"), evalResult, equalTo(new
                SchemeString("foobar")));
    }


}