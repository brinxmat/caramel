package no.greenall.caramel;

import no.greenall.caramel.util.RdfUtils;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.ResourceUtils;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.topbraid.shacl.vocabulary.SH;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@CommandLine.Command(name = "exemplify")
public class Exemplify implements Runnable {

    private static final String SHACL = "http://www.w3.org/ns/shacl#";
    private static final String TARGET_CLASS = SHACL + "targetClass";
    private static final String NODE_SHAPE = SHACL + "NodeShape";
    private static final String PROPERTY = SHACL + "property";
    private static final String PATH = SHACL + "path";

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Display help for command")
    private boolean showHelp;

    @CommandLine.Option(names = {"-i", "--input"}, description = "Path to input file", paramLabel = "FILE", required = true)
    private String inputFilePath;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Path to output file", paramLabel = "FILE")
    private String outputFilePath;


    public static void main(String[] args) {
        CommandLine.run(new Exemplify(), args);
    }

    @Override
    public void run() {
        try {
            System.out.println(exemplify());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getPrefixMap(Model input) {
        Map<String, String> prefixMapping = input.getNsPrefixMap();

        if (!prefixMapping.containsValue(SH.getURI())) {
            prefixMapping.put("sh", SH.getURI());
        }

        if (!prefixMapping.containsValue(RDFS.getURI())) {
            prefixMapping.put("rdfs", RDFS.getURI());
        }

        if (!prefixMapping.containsValue(RDF.getURI())) {
            prefixMapping.put("rdf", RDF.getURI());
        }

        return prefixMapping;
    }

    private String exemplify() {

        Model input = ModelFactory.createDefaultModel();
        Model output = ModelFactory.createDefaultModel();

        output.setNsPrefixes(getPrefixMap(input));

        Resource rootShapeNode = output.createResource();

        RDFDataMgr.read(input, inputFilePath, Lang.TURTLE);

        List<RDFNode> classes = new ArrayList<>();
        input.listObjectsOfProperty(RDF.type).forEachRemaining(classes::add);

        output.add(rootShapeNode, RDF.type, output.createResource(NODE_SHAPE));

        if (Objects.nonNull(classes.get(0))) {
            output.add(rootShapeNode,
                    output.createProperty(TARGET_CLASS),
                    output.createResource(classes.get(0).asResource()));
        }

        if (classes.size() > 1) {
            RDFList classList = output.createList(classes.toArray(new RDFNode[0]));
            Resource bnode = output.createResource();
            output.add(rootShapeNode, output.createProperty(PROPERTY), bnode);
            output.add(bnode, output.createProperty(PATH), RDF.type);
            output.add(bnode, SH.hasValue, classList);
        }

        return RdfUtils.getTurtleString(output);
    }

    private String iterateNodes(Model input) {


        Model anonymisedModel = ModelFactory.createDefaultModel();
        List<Model> modelList = new ArrayList<>();
        input.listSubjects().forEachRemaining(subject -> modelList.add(getResourceModelWithAnonymisedResources(subject)));

        return "No";

    }

    private Model getResourceModelWithAnonymisedResources(Resource resource) {

        Model model = ModelFactory.createDefaultModel();
        return model;

    }
}
