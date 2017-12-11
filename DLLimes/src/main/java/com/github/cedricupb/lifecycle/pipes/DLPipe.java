package com.github.cedricupb.lifecycle.pipes;

import com.github.cedricupb.config.dllearner.IDLConfiguration;
import com.github.cedricupb.io.dllearner.IDLLearnerJob;
import com.github.cedricupb.io.dllearner.IDLLearnerRunner;
import com.github.cedricupb.io.dllearner.inmem.InMemDLLearnerRunner;
import com.github.cedricupb.pipe.IPipe;
import com.github.cedricupb.pipe.ISink;
import org.semanticweb.owlapi.model.OWLClassExpression;

public class DLPipe implements IPipe<IDLConfiguration, OWLClassExpression> {

    private ISink<OWLClassExpression> sink;

    @Override
    public void setSink(ISink<OWLClassExpression> sink) {
        this.sink = sink;
    }

    @Override
    public void process(IDLConfiguration in) {
        if(sink == null)return;

        IDLLearnerRunner runner = new InMemDLLearnerRunner();
        IDLLearnerJob job = runner.execute(in);

        sink.process(job.queryResult());

    }
}
