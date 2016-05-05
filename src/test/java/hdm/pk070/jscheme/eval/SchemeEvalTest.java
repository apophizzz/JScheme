package hdm.pk070.jscheme.eval;

import hdm.pk070.jscheme.environment.GlobalEnvironment;
import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.type.SchemeInteger;
import hdm.pk070.jscheme.obj.type.SchemeString;
import hdm.pk070.jscheme.obj.type.SchemeSymbol;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by patrick on 05.05.16.
 */
public class SchemeEvalTest {

    private SchemeEval schemeEval;

    @Before
    public void setUp() {
        schemeEval = SchemeEval.getInstance();
    }

    @Test
    public void testEvalNumber() throws SchemeError {
        SchemeObject evalResult = schemeEval.eval(new SchemeInteger(1));

        assertThat("evalResult must not be null!", evalResult, notNullValue());
        assertThat(String.format("evalResult does not match expected type %s!", SchemeInteger.class.getSimpleName()),
                evalResult.typeOf(SchemeInteger.class), equalTo
                        (true));
        assertThat(String.format("evalResult does not match expected value %d!", 1), evalResult, equalTo(new
                SchemeInteger(1)));
    }

    @Test
    public void testEvalString() throws SchemeError {
        SchemeObject evalResult = schemeEval.eval(new SchemeString("foobar"));

        assertThat("evalResult must not be null!", evalResult, notNullValue());
        assertThat(String.format("evalResult does not match expected type %s!", SchemeString.class.getSimpleName()),
                evalResult.typeOf(SchemeString.class), equalTo(true));
        assertThat(String.format("evalResult does not match expected value %s!", "foobar"), evalResult, equalTo(new
                SchemeString("foobar")));
    }


}