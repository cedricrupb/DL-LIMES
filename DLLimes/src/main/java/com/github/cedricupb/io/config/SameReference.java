package com.github.cedricupb.io.config;

import org.semanticweb.owlapi.model.OWLIndividual;

public class SameReference {

    private OWLIndividual source;
    private OWLIndividual target;

    public SameReference(OWLIndividual source, OWLIndividual target) {
        this.source = source;
        this.target = target;
    }

    public OWLIndividual getSource() {
        return source;
    }

    public OWLIndividual getTarget() {
        return target;
    }

}
