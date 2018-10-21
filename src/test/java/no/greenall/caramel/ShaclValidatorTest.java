package no.greenall.caramel;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShaclValidatorTest {

    private static final String SIMPLEST_TTL = "simplest.ttl";
    private static final String SIMPLEST_FALSE_SHACL = "simplest_fails.shacl";
    private static final String SIMPLEST_TRUE_SHACL = "simplest_passes.shacl";

    private FileInputStream getFileInputStream(String file) throws FileNotFoundException {
        return new FileInputStream(new File(
                Objects.requireNonNull(getClass().getClassLoader().getResource(file)).getFile()));
    }

    @Test
    public void testExists() {
        Model input = ModelFactory.createDefaultModel();
        Model validation = ModelFactory.createDefaultModel();
        new ShaclValidator(input, validation);
    }

    @Test
    public void testFalse() throws FileNotFoundException {


        Model input = ModelFactory.createDefaultModel();
        Model validation = ModelFactory.createDefaultModel();

        RDFDataMgr.read(input, getFileInputStream(SIMPLEST_TTL), Lang.TURTLE);
        RDFDataMgr.read(validation, getFileInputStream(SIMPLEST_FALSE_SHACL), Lang.TURTLE);

        ShaclValidator shaclValidator = new ShaclValidator(input, validation);

        assertFalse(shaclValidator.getBooleanReport());
    }

    @Test
    public void testTrue() throws FileNotFoundException {

        Model input = ModelFactory.createDefaultModel();
        Model validation = ModelFactory.createDefaultModel();

        RDFDataMgr.read(input, getFileInputStream(SIMPLEST_TTL), Lang.TURTLE);
        RDFDataMgr.read(validation, getFileInputStream(SIMPLEST_TRUE_SHACL), Lang.TURTLE);

        ShaclValidator shaclValidator = new ShaclValidator(input, validation);

        assertTrue(shaclValidator.getBooleanReport());
    }
}
