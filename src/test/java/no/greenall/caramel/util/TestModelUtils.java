package no.greenall.caramel.util;

import org.apache.jena.rdf.model.Model;
import org.junit.Test;

import java.io.IOException;

public class TestModelUtils {

    @Test
    public void testAnonymize() throws IOException {
        Model anon_test_1 = RdfUtils.readFileToModel("anonymization_test_1.ttl");
        ModelUtils.anonymize(anon_test_1);

    }

}