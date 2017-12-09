package com.github.cedricupb.config.dllearner;

import org.dllearner.core.KnowledgeSource;
import org.semanticweb.owlapi.model.OWLIndividual;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DLConfigBuilder {

    public static DLConfigBuilder init(){
        return new DLConfigBuilder();
    }

    private Set<KnowledgeSource> ks;
    private Set<OWLIndividual> pos;
    private Set<OWLIndividual> neg;
    private boolean hasNegative;
    private long maxExec;

    private DLConfigBuilder(){
        ks = new HashSet<>();
        pos = new HashSet<>();
        neg = new HashSet<>();
        hasNegative = false;
        maxExec = 1;
    }

    public DLConfigBuilder addKnowledgeSource(KnowledgeSource ks){
        if(ks != null)
            this.ks.add(ks);
        return this;
    }

    public DLConfigBuilder addKnowledgeSources(Collection<? extends KnowledgeSource> ks){
        this.ks.addAll(ks);
        return this;
    }

    public DLConfigBuilder addPositive(OWLIndividual individual){
        if(individual != null)
            this.pos.add(individual);
        return this;
    }

    public DLConfigBuilder addPositives(Collection<? extends OWLIndividual> individual){
        if(individual != null)
            this.pos.addAll(individual);
        return this;
    }

    public DLConfigBuilder addNegative(OWLIndividual individual){
        if(individual != null) {
            this.neg.add(individual);
            hasNegative = true;
        }
        return this;
    }

    public DLConfigBuilder addNegatives(Collection<? extends OWLIndividual> individual){
        if(individual != null) {
            this.neg.addAll(individual);
            if(neg.size()>0)hasNegative = true;
        }
        return this;
    }

    public DLConfigBuilder setMaxExecutionTimeInSeconds(long time){
        maxExec = time;
        return this;
    }

    public IDLConfiguration build(){
        return new DLConfiguration(ks,pos,neg,hasNegative,maxExec);
    }


}
