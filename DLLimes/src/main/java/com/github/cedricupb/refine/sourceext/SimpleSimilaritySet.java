package com.github.cedricupb.refine.sourceext;

import org.aksw.limes.core.io.cache.Instance;
import org.aksw.limes.core.measures.measure.IMeasure;

import java.util.List;

public class SimpleSimilaritySet implements ISimilaritySet {

    private List<Instance> sourceInstances;
    private List<Instance> targetInstances;

    public SimpleSimilaritySet(List<Instance> sourceInstances, List<Instance> targetInstances) {
        this.sourceInstances = sourceInstances;
        this.targetInstances = targetInstances;
    }

    @Override
    public int score(String prop1, String prop2, IMeasure measure, double threshold) {

        int score = 0;

        for(int i = 0; i < sourceInstances.size(); i++){
            Instance src = sourceInstances.get(i);
            Instance tar = targetInstances.get(i);

            if(src.getAllProperties().contains(prop1) && tar.getAllProperties().contains(prop2)){
                double val = measure.getSimilarity(src, tar, prop1, prop2);

                if(val >= threshold)
                    score ++;
            }
        }

        return score;
    }

}
