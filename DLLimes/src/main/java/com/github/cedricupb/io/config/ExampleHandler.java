package com.github.cedricupb.io.config;

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
        for(Object o: (List)childs.get("POSITIVE")){
            if(o instanceof SameReference)pos.add((SameReference) o);
        }

        List<SameReference> neg = new ArrayList<>();
        for(Object o: (List)childs.get("NEGATIVE")){
            if(o instanceof SameReference)neg.add((SameReference) o);
        }


        return new XMLExampleConfiguration(pos, neg);
    }
}
