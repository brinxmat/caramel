package no.greenall.caramel.utils;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import java.io.ByteArrayOutputStream;

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
}
