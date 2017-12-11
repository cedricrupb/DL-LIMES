package com.github.cedricupb.io.dllearner.inmem;

import com.github.cedricupb.config.dllearner.DLConfigBuilder;
import com.github.cedricupb.config.dllearner.IDLConfiguration;

import com.github.cedricupb.io.dllearner.IDLLearnerJob;
import com.github.cedricupb.io.dllearner.IDLLearnerRunner;
import org.dllearner.kb.OWLFile;
import org.junit.Test;
import org.semanticweb.owlapi.model.IRI;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;

import static org.junit.Assert.*;

public class InMemDLLearnerRunnerTest {
    @Test
    public void execute() throws Exception {

        String uriPrefix = "http://example.com/father#";

        OWLFile ks = new OWLFile();
        ks.setFileName("/Users/cedricrichter/Documents/DL-LIMES/DLLimes/src/test/java/com/github/cedricupb/io/dllearner/inmem/father.owl");
        ks.init();

        IDLConfiguration config = DLConfigBuilder.init()
                .addKnowledgeSource(ks)
                .addPositive(new OWLNamedIndividualImpl(IRI.create(uriPrefix + "stefan")))
                .addPositive(new OWLNamedIndividualImpl(IRI.create(uriPrefix + "markus")))
                .addPositive(new OWLNamedIndividualImpl(IRI.create(uriPrefix + "martin")))
                .addNegative(new OWLNamedIndividualImpl(IRI.create(uriPrefix + "heinz")))
                .addNegative(new OWLNamedIndividualImpl(IRI.create(uriPrefix + "anna")))
                .addNegative(new OWLNamedIndividualImpl(IRI.create(uriPrefix + "michelle")))
                .setMaxExecutionTimeInSeconds(1)
                .build();

        IDLLearnerRunner runner = new InMemDLLearnerRunner();
        IDLLearnerJob job = runner.execute(config);

        System.out.println(job.queryResult());
    }

}