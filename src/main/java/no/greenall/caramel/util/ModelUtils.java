package no.greenall.caramel.util;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;

public class ModelUtils {
    public static Model anonymize(Model input) {
        Model result = ModelFactory.createDefaultModel();

        input.listSubjects().forEachRemaining(subject -> result.add(anonymizeResource(input.getResource(subject.getURI())).getModel()));

        return result;
    }

    private static Resource anonymizeResource(Resource resource) {
        Model model = ModelFactory.createDefaultModel();
        Resource temporaryResource = model.createResource();

        if (!resource.isAnon()) {

            resource.getModel().listStatements().forEachRemaining(statement ->
                    model.add(model.createStatement(
                            temporaryResource,
                            statement.getPredicate(),
                            statement.getObject())));
            resource = model.getResource(temporaryResource.getURI());
        }
        return resource;
    }
}
