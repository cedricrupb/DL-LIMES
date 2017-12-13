package com.github.cedricupb.io.config;

import org.xml.sax.Attributes;

import java.util.Map;

public class DefaultXMLHandler implements IXMLEventHandler {
    @Override
    public boolean isHandling(String path, String tag) {
        return true;
    }

    @Override
    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context) {
        if(childs.size()==1 && childs.containsKey("content")){
            return childs.get("content");
        }
        return childs;
    }
}
