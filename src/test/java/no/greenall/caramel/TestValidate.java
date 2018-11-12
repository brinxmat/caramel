package no.greenall.caramel;

import no.greenall.caramel.util.ResourceUtils;
import no.greenall.caramel.util.RdfUtils;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestValidate {

    private static final String EOL = System.lineSeparator();
    private static final String INPUT_FILE_ARGUMENT = Option.INPUT.prefixedShortOpt() + "=simplest.ttl";
    private static final String SHACL_FILE_ARGUMENT = Option.SHACL.prefixedShortOpt() + "=simplest_fails.shacl";
    private static final String REPORT_FORMAT_ARGUMENT_BOOLEAN = Option.FORMAT.prefixedShortOpt() + "=boolean";
    private static final String REPORT_FORMAT_ARGUMENT_TEXT = Option.FORMAT.prefixedShortOpt() + "=text";
    private static final String FALSE = String.format("false%s", EOL);
    private static final String MISSING_OPTION = "Missing required option ";
    private static final String HELP_MESSAGE = String.format(
            "Usage: validate [-h] -i=FILE [-o=FILE] [-r=boolean|text] -s=FILE%1$s"
                    + "  -h, --help          Display help for command%1$s"
                    + "  -i, --input=FILE    Path to input file%1$s"
                    + "  -o, --output=FILE   Path to output file%1$s"
                    + "  -r, --report-format=boolean|text%1$s"
                    + "                      Report format, boolean or text%1$s"
                    + "  -s, --shacl=FILE    Path to shacl validation file%1$s", EOL);
    private static final String TEXT_OUTPUT_VALIDATION_TXT = "text_output_validation.txt";

    @Rule
    public TestResources testResources = TestResources.getDefaultTestResource();

    @Test
    public void testExists() {
        new Validate();
    }

    @Test
    public void testHelp() {
        String[] emptyArgs = {Option.HELP.prefixedLongOpt()};
        String[] shortOpt = {Option.HELP.prefixedShortOpt()};
        String[] longOpt = {Option.HELP.prefixedLongOpt()};

        Validate.main(emptyArgs);
        assertEquals(HELP_MESSAGE, testResources.getStandardOutString());

        testResources.resetStandardOutString();
        Validate.main(shortOpt);
        assertEquals(HELP_MESSAGE, testResources.getStandardOutString());

        testResources.resetStandardOutString();
        Validate.main(longOpt);
        assertEquals(HELP_MESSAGE, testResources.getStandardOutString());

    }

    @Test
    public void testInputFileNoValidationFile() {

        String validationOption = "'--shacl=FILE'";

        String[] args = {INPUT_FILE_ARGUMENT};
        Validate.main(args);
        String expected = MISSING_OPTION +
                validationOption +
                EOL +
                HELP_MESSAGE;
        assertEquals(expected, testResources.getStandardErrorString());
    }

    @Test
    public void testInputFile() {
        String[] args = {INPUT_FILE_ARGUMENT, SHACL_FILE_ARGUMENT};
        Validate.main(args);
        assertEquals(FALSE, testResources.getStandardOutString());
    }

    @Test
    public void testBooleanOutputStandardOut() {
        String[] args = {INPUT_FILE_ARGUMENT, SHACL_FILE_ARGUMENT, REPORT_FORMAT_ARGUMENT_BOOLEAN};
        Validate.main(args);
        assertEquals(FALSE, testResources.getStandardOutString());
    }

    @Test
    public void testDefaultOutputStandardOut() {
        String[] args = {INPUT_FILE_ARGUMENT, SHACL_FILE_ARGUMENT};
        Validate.main(args);
        assertEquals(FALSE, testResources.getStandardOutString());
    }

    @Test
    public void testTextOutputStandardOut() throws IOException {
        String expected = ResourceUtils.readString(TEXT_OUTPUT_VALIDATION_TXT, true);
        String[] args = {INPUT_FILE_ARGUMENT, SHACL_FILE_ARGUMENT, REPORT_FORMAT_ARGUMENT_TEXT};
        Validate.main(args);
        assertEquals(expected, RdfUtils.squashBlankNodes(testResources.getStandardOutString(), "_:b0"));
    }
}
