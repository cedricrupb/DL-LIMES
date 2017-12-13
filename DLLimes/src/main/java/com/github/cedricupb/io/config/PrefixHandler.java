package com.github.cedricupb.io.config;

import org.xml.sax.Attributes;

import java.util.HashMap;
import java.util.Map;

public class PrefixHandler implements IXMLEventHandler {
    @Override
    public boolean isHandling(String path, String tag) {
        return path.endsWith("REFINE") && tag.equals("PREFIX");
    }

    @Override
    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context) {

        Map<String, String> prefixes;
        if(context.containsKey("prefix")){
            prefixes = (Map<String, String>)context.get("prefix");
        }else{
            prefixes = new HashMap<>();
            context.put("prefix", prefixes);
        }

        String key = (String)childs.get("NAMESPACE");
        String value = (String)childs.get("LABEL");
        prefixes.put(key, value);

        return null;
    }
}
