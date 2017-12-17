package com.github.cedricupb.io.config;

import org.xml.sax.Attributes;

import java.util.Map;

public class TerminateHandler implements IXMLEventHandler {
    @Override
    public boolean isHandling(String path, String tag) {
        return path.endsWith("REFINE") && tag.equals("TERMINATE");
    }

    @Override
    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context) {
        int iterations = Integer.parseInt((String)childs.get("ITERATION"));
        boolean fix = Boolean.parseBoolean((String)childs.get("FIXPOINT"));
        String file = (String)childs.get("FILE");

        return new TerminateConfig(iterations, fix, file);
    }
}
