package com.github.cedricupb.io.config;

import org.semanticweb.owlapi.model.OWLIndividual;

import java.util.List;

public class XMLExampleConfiguration {

    private List<SameReference> positive;
    private List<SameReference> negative;

    public XMLExampleConfiguration(List<SameReference> positive, List<SameReference> negative) {
        this.positive = positive;
        this.negative = negative;
    }

    public List<SameReference> getPositive() {
        return positive;
    }

    public List<SameReference> getNegative() {
        return negative;
    }

}
