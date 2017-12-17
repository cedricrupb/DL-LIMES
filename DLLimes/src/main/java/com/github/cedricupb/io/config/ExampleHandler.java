package com.github.cedricupb.io.config;

import com.clarkparsia.pellet.rules.rete.WME;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExampleHandler implements IXMLEventHandler {
    @Override
    public boolean isHandling(String path, String tag) {
        return tag.equals("EXAMPLES");
    }

    @Override
    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context) {
        List<SameReference> pos = new ArrayList<>();
        Object o = childs.get("POSITIVE");

        if(o instanceof List){
            pos.addAll((List<SameReference>)o);
        }else{
            pos.add((SameReference)o);
        }

        List<SameReference> neg = new ArrayList<>();
        o = childs.get("NEGATIVE");

        if(o instanceof List){
            neg.addAll((List<SameReference>)o);
        }else{
            neg.add((SameReference)o);
        }


        return new XMLExampleConfiguration(pos, neg);
    }
}
