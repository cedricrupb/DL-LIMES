package com.github.cedricupb.lifecycle.pipes;

import com.github.cedricupb.pipe.IPipe;
import com.github.cedricupb.pipe.ISink;
import org.semanticweb.owlapi.model.OWLClassExpression;

public class RestrictionPipe implements IPipe<OWLClassExpression, String> {

    private ISink<String> sink;

    @Override
    public void setSink(ISink<String> sink) {
        this.sink = sink;
    }

    @Override
    public void process(OWLClassExpression in) {
        if(sink == null)return;

        String out = null;
        //TODO: Write translation of OWLClass to restriction here


        sink.process(out);
    }
}
