package hdm.pk070.jscheme.obj.builtin.syntax;

import hdm.pk070.jscheme.error.SchemeError;
import hdm.pk070.jscheme.obj.SchemeObject;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeCons;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeNil;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeString;
import hdm.pk070.jscheme.obj.builtin.simple.SchemeSymbol;
import hdm.pk070.jscheme.table.environment.Environment;
import hdm.pk070.jscheme.table.environment.LocalEnvironment;
import hdm.pk070.jscheme.table.environment.entry.EnvironmentEntry;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;


/**
 * A test class for {@link SchemeBuiltinQuote}.
 *
 * @author patrick.kleindienst
 */
public class SchemeBuiltinQuoteTest {

    private SchemeBuiltinSyntax schemeQuote;
    private Environment<SchemeSymbol, EnvironmentEntry> dummyEnvironment;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.schemeQuote = SchemeBuiltinQuote.create();
        this.dummyEnvironment = LocalEnvironment.withSize(42);
    }

    @Test
    public void testApplyThrowsErrorOnEmptyArgList() throws SchemeError {
        this.expectedException.expect(SchemeError.class);
        this.expectedException.expectMessage("(quote): bad syntax in: (quote) [expected 1 argument, 0 given]");

        this.schemeQuote.apply(new SchemeNil(), dummyEnvironment);
    }

    @Test
    public void testApplyThrowsErrorOnMoreThanOneArgument() throws SchemeError {
        this.expectedException.expect(SchemeError.class);
        this.expectedException.expectMessage("(quote): bad syntax in: (quote) [expected 1 argument, more given]");

        this.schemeQuote.apply(new SchemeCons(new SchemeSymbol("foo"), new SchemeCons(
                new SchemeString("Invalid argument"), new SchemeNil())), dummyEnvironment);
    }

    @Test
    public void testApplyReturnsUnevaluatedArg() throws SchemeError {
        SchemeObject result = this.schemeQuote.apply(new SchemeCons(new SchemeSymbol("foobar"), new SchemeNil()),
                dummyEnvironment);

        assertThat("Result may not be null!", result, notNullValue());
        assertThat("Result does not match expected type!", result.getClass(), equalTo(SchemeSymbol.class));
        assertThat("Result does not match expected value!", result, equalTo(new SchemeSymbol("foobar")));
    }
}