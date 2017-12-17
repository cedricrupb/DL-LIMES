package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.io.config.SameReference;
import com.github.cedricupb.io.config.XMLRunningConfiguration;
import com.github.cedricupb.lifecycle.IPhase;
import com.github.cedricupb.lifecycle.IPhaseState;
import org.aksw.limes.core.io.mapping.AMapping;
import org.aksw.limes.core.io.mapping.MappingFactory;
import org.aksw.limes.core.io.mapping.writer.IMappingWriter;
import org.aksw.limes.core.io.mapping.writer.RDFMappingWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultEmittingPhase implements IPhase {

    private IPhaseState state;
    private boolean running = false;

    @Override
    public void pushState(IPhaseState state) {
        this.state = state;
    }

    @Override
    public IPhase getNext() {
        return null;
    }

    @Override
    public boolean isPhaseRunning() {
        return running;
    }

    @Override
    public void onShutdown() {
        running = false;
    }

    @Override
    public void run() {
        running = true;

        System.out.println("(Iteration "+state.getProperty("iterations")+") Saving results...");

        XMLRunningConfiguration config = (XMLRunningConfiguration)state.getProperty("configuration");

        List<SameReference> pos = (List<SameReference>) state.getProperty("positiveExamples");

        AMapping mapping = MappingFactory.createDefaultMapping();
        mapping.setPredicate("http://www.w3.org/2002/07/owl#sameAs");

        Map<String, HashMap<String, Double>> inMap = mapping.getMap();

        for(SameReference ref: pos){
            String src = ref.getSource().toStringID();
            if(!inMap.containsKey(src)){
                inMap.put(src, new HashMap<>());
            }
            Map<String, Double> refMap = inMap.get(src);

            String target = ref.getTarget().toStringID();
            refMap.put(target, 1.0);
        }

        IMappingWriter writer = new RDFMappingWriter();

        try {
            System.out.println("Write mapping to "+config.getTerminate().getOutFile());
            writer.write(mapping, config.getTerminate().getOutFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        running = false;
    }
}
