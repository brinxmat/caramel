package no.greenall.caramel.util;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class RdfUtils {
    public static String getTurtleString(Model input) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        RDFDataMgr.write(outputStream, input, Lang.TURTLE);
        return  outputStream.toString();
    }

    public static String squashBlankNodes(String input, String replacement) {
        String bnodePattern = "_:[A-Za-z][A-Za-z0-9]*";
        return input.replaceAll(bnodePattern, replacement);
    }

    public static Model readFileToModel(String filename) throws IOException {
        Model result = ModelFactory.createDefaultModel();
        String data = ResourceUtils.readString(filename, true);
        RDFDataMgr.read(result, new ByteArrayInputStream(data.getBytes()), Lang.TURTLE);
        return result;
    }
}
