package no.greenall.caramel;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import picocli.CommandLine;

import java.io.IOException;

@CommandLine.Command(name = "validate")
public class Validate implements Runnable{

    private static final String DEFAULT_REPORT_FORMAT = "boolean";
    private static final String BOOLEAN = "boolean";
    private static final String TEXT = "text";

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Display help for command")
    private boolean showHelp;

    @CommandLine.Option(names = {"-i", "--input"}, description = "Path to input file", paramLabel = "FILE", required = true)
    private String inputFilePath;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Path to output file", paramLabel = "FILE")
    private String outputFilePath;

    @CommandLine.Option(names = {"-s", "--shacl"}, description = "Path to shacl validation file", paramLabel = "FILE", required = true)
    private String shaclFilePath;

    @CommandLine.Option(names = {"-r", "--report-format"}, paramLabel = "boolean|text", description = "Report format, boolean or text")
    private String reportFormat = DEFAULT_REPORT_FORMAT;

    public static void main(String[] args) {
        CommandLine.run(new Validate(), args);
    }

    private String validate() throws IOException {
        Model inputModel = ModelFactory.createDefaultModel();
        Model shaclModel = ModelFactory.createDefaultModel();

        RDFDataMgr.read(inputModel, inputFilePath, Lang.TURTLE);
        RDFDataMgr.read(shaclModel, shaclFilePath, Lang.TURTLE);

        ShaclValidator shaclValidator = new ShaclValidator(inputModel, shaclModel);

        switch (reportFormat.toLowerCase()) {
            case TEXT:
                return shaclValidator.getTextualReport();
            case BOOLEAN:
            default:
                return String.valueOf(shaclValidator.getBooleanReport());
        }
    }

    @Override
    public void run() {
        try {
            System.out.println(validate());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
