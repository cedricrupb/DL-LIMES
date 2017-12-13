package com.github.cedricupb.io.config;

import org.aksw.limes.core.io.config.KBInfo;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.xml.sax.Attributes;

import java.util.List;
import java.util.Map;

public class RefineHandler implements IXMLEventHandler {
    @Override
    public boolean isHandling(String path, String tag) {
        return tag.equals("REFINE");
    }

    @Override
    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context) {
        KBInfo source = (KBInfo)childs.get("SOURCE");
        KBInfo target = (KBInfo)childs.get("TARGET");
        XMLExampleConfiguration examples = (XMLExampleConfiguration)childs.get("EXAMPLES");
        return new XMLRefineConfiguration(source, target, examples);
    }
}
