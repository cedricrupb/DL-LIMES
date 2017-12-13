package com.github.cedricupb.io.config;

import com.github.cedricupb.io.dllearner.inmem.InMemDLLearnerRunner;
import org.xml.sax.Attributes;

import java.util.Map;

public class DLHandler implements IXMLEventHandler {
    @Override
    public boolean isHandling(String path, String tag) {
        return path.endsWith("INTERFACE") && tag.equals("DLLEARNER");
    }

    @Override
    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context) {
        String type = (String)childs.get("TYPE");

        if(type.equals("InMem")){
            return new InMemDLLearnerRunner();
        }

        return null;
    }
}
