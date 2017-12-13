package com.github.cedricupb.io.config;

import com.github.cedricupb.io.dllearner.IDLLearnerRunner;
import com.github.cedricupb.io.limes.ILIMESRunner;
import org.xml.sax.Attributes;

import java.util.Map;

public class RunningHandler implements IXMLEventHandler {
    @Override
    public boolean isHandling(String path, String tag) {
        return tag.equals("CONFIG");
    }

    @Override
    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context) {

        Map<String, Object> inter = (Map<String, Object>)childs.get("INTERFACE");
        XMLRefineConfiguration refine = (XMLRefineConfiguration)childs.get("REFINE");


        return new XMLRunningConfiguration((ILIMESRunner)inter.get("LIMES"),
                                            (IDLLearnerRunner)inter.get("DLLEARNER"),
                                            refine);
    }
}
