package hdm.pk070.jscheme.reader;

import org.junit.After;
import org.junit.Test;

import java.util.Objects;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author patrick.kleindienst
 */
public class SchemeReaderTest {

    private SchemeReader schemeReader;


    @Test
    public void testCreateSchemeReaderInstance() {
        SchemeReader schemeReaderStdIn = SchemeReader.withStdin();
        SchemeReader schemeReaderWithStream = SchemeReader.withInputStream(System.in);

        assertThat(schemeReaderStdIn, notNullValue());
        assertThat(schemeReaderWithStream, notNullValue());
    }

    @Test(expected = NullPointerException.class)
    public void testPassNullToCreationMethodThrowsException() {
        SchemeReader.withInputStream(null);
    }


    @After
    public void tearDown() {
        if (Objects.nonNull(schemeReader)) {
            schemeReader.shutdown();
        }
    }


}