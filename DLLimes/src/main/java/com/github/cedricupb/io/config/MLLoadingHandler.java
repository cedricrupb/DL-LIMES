package com.github.cedricupb.io.config;

import org.aksw.limes.core.ml.algorithm.LearningParameter;
import org.aksw.limes.core.ml.algorithm.MLAlgorithmFactory;
import org.xml.sax.Attributes;

import java.util.List;
import java.util.Map;

public class MLLoadingHandler implements IXMLEventHandler {
    @Override
    public boolean isHandling(String path, String tag) {
        return tag.equals("MLALGORITHM");
    }

    @Override
    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context) {

        MLConfig config = new MLConfig((String)childs.get("NAME"),
        Double.parseDouble((String)childs.get("THRESHOLD")));

        if(childs.containsKey("TYPE")){
            config.setMlImplementationType(
                    MLAlgorithmFactory.getImplementationType((String)childs.get("TYPE"))
            );
        }

        if(childs.containsKey("TRAINING")){
            config.setMlTrainingDataFile(
                    (String)childs.get("TRAINING")
            );
        }

        if(childs.containsKey("PARAMETER")){
            Object o = childs.get("PARAMETER");
            if(o instanceof List){
                for(LearningParameter p: (List<LearningParameter>)o){
                    config.getMlAlgorithmParameters().add(p);
                }
            }else if(o instanceof LearningParameter){
                config.getMlAlgorithmParameters().add((LearningParameter)o);
            }
        }

        return config;
    }
}
