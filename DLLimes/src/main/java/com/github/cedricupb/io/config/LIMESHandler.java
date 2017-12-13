package com.github.cedricupb.io.config;

import com.github.cedricupb.io.limes.impl.InMemLIMESRunner;
import org.xml.sax.Attributes;

import java.util.Map;

public class LIMESHandler implements IXMLEventHandler {
    @Override
    public boolean isHandling(String path, String tag) {
        return path.endsWith("INTERFACE") && tag.equals("LIMES");
    }

    @Override
    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context) {

        String type = (String)childs.get("TYPE");

        if(type.equals("InMem")){
            return new InMemLIMESRunner();
        }

        return null;
    }
}
