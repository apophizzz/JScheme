package hdm.pk070.jscheme.reader;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * @author patrick.kleindienst
 */
public class PushbackReaderLearningTest {

    private PushbackReader pushbackReader;
    private String testString;


    @Before
    public void setUp() {
        testString = "foobar";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(testString.getBytes());
        pushbackReader = new PushbackReader(new InputStreamReader(byteArrayInputStream), 10);
    }

    @Test
    public void testUnreadCharacter() {
        char ch = readFromPushbackReader();
        assertThat(ch, equalTo(testString.charAt(0)));

        unreadCharacterToPushbackReader(ch);
        char ch2 = readFromPushbackReader();
        assertThat(ch2, equalTo(ch));
        assertThat(ch2, equalTo(testString.charAt(0)));
    }

    @Test
    public void testReadAndUnreadMultipleCharacters() throws Exception {
        char c1 = readFromPushbackReader();
        char c2 = readFromPushbackReader();
        unreadCharacterToPushbackReader(c2);
        unreadCharacterToPushbackReader(c1);

        assertThat("PushbackReader did not provide expected character!", readFromPushbackReader(), equalTo(c1));
        assertThat("PushbackReader did not provide expected character!", readFromPushbackReader(), equalTo(c2));
    }

    private char readFromPushbackReader() {
        int ch = 0;
        try {
            ch = pushbackReader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (char) ch;
    }

    private void unreadCharacterToPushbackReader(char ch) {
        try {
            pushbackReader.unread(ch);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
