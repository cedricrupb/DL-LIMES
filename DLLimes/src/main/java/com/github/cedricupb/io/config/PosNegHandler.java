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

        if(context.containsKey("prefix")){
            Map<String, String> prefixes = (Map<String, String>)context.get("prefix");
            source = resolvePrefix(prefixes, source);
            target = resolvePrefix(prefixes, target);
        }

        return new SameReference(
                new OWLNamedIndividualImpl(IRI.create(source)),
                new OWLNamedIndividualImpl(IRI.create(target))
        );
    }

    private String resolvePrefix(Map<String, String> prefixes, String res){
        if(res.contains(":")){
            String pre = res.substring(0, res.indexOf(":"));
            String rest = res.substring(res.indexOf(":")+1);
            if(prefixes.containsKey(pre)){
                return prefixes.get(pre)+rest;
            }
        }
        return res;
    }
}
