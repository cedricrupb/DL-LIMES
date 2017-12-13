package com.github.cedricupb.io.config;

import org.semanticweb.owlapi.model.IRI;
import org.xml.sax.Attributes;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;

import java.util.Map;

public class PosNegHandler implements IXMLEventHandler {
    @Override
    public boolean isHandling(String path, String tag) {
        return tag.equals("POSITIVE") || tag.equals("NEGATIVE");
    }

    @Override
    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context) {

        String source = (String) childs.get("SOURCE");
        String target = (String) childs.get("TARGET");

        return new SameReference(
                new OWLNamedIndividualImpl(IRI.create(source)),
                new OWLNamedIndividualImpl(IRI.create(target))
        );
    }
}
