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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SameReference)) return false;

        SameReference that = (SameReference) o;

        if (getSource() != null ? !getSource().equals(that.getSource()) : that.getSource() != null) return false;
        return getTarget() != null ? getTarget().equals(that.getTarget()) : that.getTarget() == null;
    }

    @Override
    public int hashCode() {
        int result = getSource() != null ? getSource().hashCode() : 0;
        result = 31 * result + (getTarget() != null ? getTarget().hashCode() : 0);
        return result;
    }

}
