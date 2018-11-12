package no.greenall.caramel;

import no.greenall.caramel.util.ResourceUtils;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestExemplify {
    private static final String EXEMPLIFICATION_HELP_TXT = "exemplification_help.txt";
    private static final String EOL = System.lineSeparator();
    private static final String MISSING_REQUIRED_OPTION_FILE = "Missing required option '--input=FILE'" + EOL;
    private static final String FLAG_INPUT_SIMPLE_EXEMPLIFICATION_INPUT_TXT = "--input=simple_exemplification_input.ttl";
    private static final String SIMPLE_EXEMPLIFICATION_OUTPUT_TXT = "simple_exemplification_output.txt";

    @Rule
    public TestResources testResources = TestResources.getDefaultTestResource();

    @Test
    public void testExists() {
        new Exemplify();
    }

    @Test
    public void testExemplifyHelp() throws IOException {
        String expected = MISSING_REQUIRED_OPTION_FILE + ResourceUtils.readString(EXEMPLIFICATION_HELP_TXT, true);
        String[] emptyArgs = {};
        Exemplify.main(emptyArgs);
        assertEquals(expected, testResources.getStandardErrorString());
    }

    @Test
    public void testExemplixfy() throws IOException {
        String simpleOutput = ResourceUtils.readString(SIMPLE_EXEMPLIFICATION_OUTPUT_TXT, true);

        String[] args = {FLAG_INPUT_SIMPLE_EXEMPLIFICATION_INPUT_TXT};
        Exemplify.main(args);
        assertEquals(simpleOutput, testResources.getStandardOutString());
    }
}
