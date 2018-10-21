package no.greenall.caramel;

import no.greenall.caramel.util.ResourceUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class TestApp {

    ByteArrayOutputStream byteArrayOutputStream;
    PrintStream terminal;

    @Before
    public void setup() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        terminal = System.out;
        System.setOut(new PrintStream(byteArrayOutputStream));
    }

    @After
    public void tearDown() {
        System.setOut(terminal);
    }

    private static final String EOL = System.lineSeparator();

    @Test
    public void testAppExists() {
        new App();
    }

    @Test
    public void testCommandLineHelp() throws IOException {

        String expected = ResourceUtils.readString("app_help_message.txt", true);

        String[] emptyArgs = {Option.HELP.prefixedLongOpt()};

        App.main(emptyArgs);

        assertEquals(expected, getTerminalString());

    }

    private Object getTerminalString() {
        return byteArrayOutputStream.toString();
    }
}
