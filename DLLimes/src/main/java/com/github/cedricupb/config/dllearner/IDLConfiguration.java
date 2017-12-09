package com.github.cedricupb.config.dllearner;

import org.dllearner.core.KnowledgeSource;
import org.semanticweb.owlapi.model.OWLIndividual;

import java.util.List;
import java.util.Set;

public interface IDLConfiguration {

    public Set<KnowledgeSource> getSources();

    public boolean hasNegativeExamples();

    public Set<OWLIndividual> getPositive();

    public Set<OWLIndividual> getNegative();

    public long getMaxExecutionTime();

}
