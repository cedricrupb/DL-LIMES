package com.github.cedricupb.config.dllearner;

import org.dllearner.core.KnowledgeSource;
import org.semanticweb.owlapi.model.OWLIndividual;

import java.util.Collections;
import java.util.Set;

public class DLConfiguration implements IDLConfiguration{

    private Set<KnowledgeSource> ks;
    private Set<OWLIndividual> pos;
    private Set<OWLIndividual> neg;
    private boolean hasNegative;
    private long maxExec;

    DLConfiguration(Set<KnowledgeSource> ks, Set<OWLIndividual> pos,
                    Set<OWLIndividual> neg, boolean hasNegative, long maxExec) {
        this.ks = ks;
        this.pos = pos;
        this.neg = neg;
        this.hasNegative = hasNegative;
        this.maxExec = maxExec;
    }


    @Override
    public Set<KnowledgeSource> getSources() {
        return Collections.unmodifiableSet(ks);
    }

    @Override
    public boolean hasNegativeExamples() {
        return hasNegative;
    }

    @Override
    public Set<OWLIndividual> getPositive() {
        return Collections.unmodifiableSet(pos);
    }

    @Override
    public Set<OWLIndividual> getNegative() {
        return Collections.unmodifiableSet(neg);
    }

    @Override
    public long getMaxExecutionTime() {
        return maxExec;
    }
}
