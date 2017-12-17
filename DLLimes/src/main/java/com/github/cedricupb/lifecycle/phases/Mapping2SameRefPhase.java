package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.io.config.SameReference;
import com.github.cedricupb.lifecycle.IPhase;
import org.aksw.limes.core.io.mapping.AMapping;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLIndividual;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mapping2SameRefPhase extends APhase {

    public Mapping2SameRefPhase(IPhase next) {
        super(next);
    }

    @Override
    public void run() {
        running = true;

        AMapping mapping = (AMapping)state.getProperty("sameMapping");

        List<SameReference> positives = new ArrayList<>();

        for(Map.Entry<String, HashMap<String, Double>> e: mapping.getMap().entrySet()){

            OWLIndividual src = new OWLNamedIndividualImpl(IRI.create(e.getKey()));

            for(String t: e.getValue().keySet()){
                positives.add(new SameReference(src, new OWLNamedIndividualImpl(IRI.create(t))));
            }
        }

        state.setProperty("sameMapping", null);
        state.setProperty("positiveExamples", positives);

        running = false;
    }
}
