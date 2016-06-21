package hdm.pk070.jscheme.obj.type;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

/**
 * A test class for {@link SchemeCons}
 *
 * @author patrick.kleindienst
 */
public class SchemeConsTest {

    private SchemeCons cons1;
    private SchemeCons cons2;
    private SchemeCons cons3;

    @Before
    public void setUp() {
        cons1 = new SchemeCons(new SchemeInteger(42), new SchemeCons(new SchemeInteger(43), new SchemeNil
                ()));
        cons2 = new SchemeCons(new SchemeInteger(42), new SchemeCons(new SchemeInteger(43), new SchemeNil
                ()));
        cons3 = new SchemeCons(new SchemeString("foobar"), new SchemeNil());
    }

    @Test
    public void testEquals() {
        assertThat("cons1 and cons2 have to be equal!", cons1, equalTo(cons2));
        assertThat("cons2 and cons1 have to be equal!", cons2, equalTo(cons1));
        assertThat("cons1 and cons3 must not be equal!", cons1, not(equalTo(cons3)));
        assertThat("cons2 and cons3 must not be equal!", cons2, not(equalTo(cons3)));
    }

    @Test
    public void testHashCode() {
        assertThat("hashcode of cons1 and cons2 must be equal!", cons1.hashCode(), equalTo(cons2.hashCode()));
    }

    @Test
    public void testHashCodeWithHashContainer() {
        HashSet<SchemeCons> hashSet = new HashSet<>();
        hashSet.add(cons1);
        hashSet.add(cons3);

        boolean containsCons1 = hashSet.contains(new SchemeCons(new SchemeInteger(42), new SchemeCons(new SchemeInteger
                (43), new SchemeNil())));
        boolean containsCons3 = hashSet.contains(new SchemeCons(new SchemeString("foobar"), new SchemeNil()));

        assertThat(containsCons1, equalTo(true));
        assertThat(containsCons3, equalTo(true));
        assertThat(hashSet.add(cons2), equalTo(false));
    }
}