package no.greenall.caramel;

import no.greenall.caramel.util.ResourceUtils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.topbraid.shacl.validation.ValidationUtil;

import java.io.IOException;

class ShaclValidator {

    private static final Property SH_CONFORMS = ResourceFactory.createProperty("http://www.w3.org/ns/shacl#conforms");
    private static final Literal BOOLEAN_FALSE = ResourceFactory.createTypedLiteral("false", XSDDatatype.XSDboolean);

    private static Model validationReport;

    ShaclValidator(Model input, Model validation) {
        validationReport = ValidationUtil.validateModel(input, validation, true).getModel();
    }

    boolean getBooleanReport() {
        return !validationReport.contains(null, SH_CONFORMS, BOOLEAN_FALSE);
    }

    String getTextualReport() throws IOException {
        QueryExecution queryExecution = QueryExecutionFactory
                .create(ResourceUtils.readString("validation_report_textual.sparql", true), validationReport);
        ResultSet resultSet = queryExecution.execSelect();

        return ResultSetFormatter.asText(resultSet);
    }


}
