package no.greenall.caramel.util;

import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RdfUtilsTest {

    @Test
    public void testGetTurtleString() {

        String subject = "http://example.org/a";
        String property = "http://example.org/b";
        String object = "http://example.org/c";

        Model model = ModelFactory.createDefaultModel();
        model.add(getTestModel(subject, property, object));

        String resultData = RdfUtils.getTurtleString(model);

        assertTrue(resultData.contains(subject));
        assertTrue(resultData.contains(property));
        assertTrue(resultData.contains(object));

        Model resultModel = ModelFactory.createDefaultModel();

        RDFDataMgr.read(resultModel, new ByteArrayInputStream(resultData.getBytes()), Lang.TURTLE);
        assertTrue(model.isIsomorphicWith(resultModel));
    }

    private Statement getTestModel(Resource subject, String property, Resource object) {
        Model model = ModelFactory.createDefaultModel();
        Statement statement = model.createStatement(subject, model.createProperty(property), object);
        return statement;
    }

    private Statement getTestModel(Resource subject, String property, String object) {
        Model model = ModelFactory.createDefaultModel();
        return model.createStatement(
                subject, model.createProperty(property), model.createLiteral(object));
    }

    private Statement getTestModel(String subject, String property, String object) {
        Model model = ModelFactory.createDefaultModel();
        return model.createStatement(
                model.createResource(subject), model.createProperty(property), model.createLiteral(object));
    }

    @Test
    public void itCanSquashBlankNodes() {
        String input = "_:b01 <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> _:b02 .";
        String replacement = "__yes__";
        String expected = replacement + " <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> " + replacement + " .";

        String result = RdfUtils.squashBlankNodes(input, replacement);
        assertEquals(expected, result);
    }

    @Test
    public void itCanReadFileToModel() throws IOException {

        Model expected = ModelFactory.createDefaultModel();
        Resource bnode = expected.createResource();
        expected.add(getTestModel(
                bnode,
                "http://www.w3.org/1999/02/22-rdf-syntax-ns#type",
                expected.createResource("http://www.w3.org/2000/01/rdf-schema#Class")));
        expected.add(getTestModel(
                bnode,
                "http://www.w3.org/2000/01/rdf-schema#label",
                "Simplest"));

        assertTrue(expected.isIsomorphicWith(RdfUtils.readFileToModel("simplest.ttl")));
    }
}