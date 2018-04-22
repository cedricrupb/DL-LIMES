package com.github.cedricupb.io.dllearner.inmem;

import com.github.cedricupb.config.dllearner.IDLConfiguration;
import com.github.cedricupb.io.dllearner.IDLLearnerJob;
import com.github.cedricupb.io.dllearner.IDLLearnerRunner;
import org.semanticweb.owlapi.model.OWLClassExpression;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// The class will process the incoming dl learner configuration.
public class InMemDLLearnerRunner implements IDLLearnerRunner {
    @Override
    public IDLLearnerJob execute(IDLConfiguration config) {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Callable<OWLClassExpression> call = new InMemDLLearnerJob.JobCallable(config);

        return new InMemDLLearnerJob(service, service.submit(call));
    }
}
