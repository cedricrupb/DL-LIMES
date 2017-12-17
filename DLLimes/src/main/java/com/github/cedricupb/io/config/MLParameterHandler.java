package com.github.cedricupb.io.config;

import org.aksw.limes.core.ml.algorithm.LearningParameter;
import org.xml.sax.Attributes;

import java.util.Map;

public class MLParameterHandler implements IXMLEventHandler {
    @Override
    public boolean isHandling(String path, String tag) {
        return path.endsWith("MLALGORITHM") && tag.equals("PARAMETER");
    }

    @Override
    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context) {

        LearningParameter param = new LearningParameter();

        param.setName((String)childs.get("NAME"));
        param.setValue((String)childs.get("VALUE"));

        return null;
    }
}
