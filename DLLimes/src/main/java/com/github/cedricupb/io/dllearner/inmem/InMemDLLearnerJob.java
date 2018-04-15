package com.github.cedricupb.io.dllearner.inmem;

import com.github.cedricupb.config.dllearner.IDLConfiguration;
import com.github.cedricupb.io.dllearner.IDLLearnerJob;
import org.dllearner.algorithms.celoe.CELOE;
import org.dllearner.core.AbstractClassExpressionLearningProblem;
import org.dllearner.core.AbstractReasonerComponent;
import org.dllearner.core.KnowledgeSource;
import org.dllearner.core.Score;
import org.dllearner.kb.OWLFile;
import org.dllearner.kb.SparqlEndpointKS;
import org.dllearner.kb.sparql.SparqlKnowledgeSource;
import org.dllearner.learningproblems.PosNegLPStandard;
import org.dllearner.learningproblems.PosOnlyLP;
import org.dllearner.reasoning.ClosedWorldReasoner;
import org.dllearner.reasoning.SPARQLReasoner;
import org.semanticweb.owlapi.model.OWLClassExpression;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class InMemDLLearnerJob implements IDLLearnerJob {

    private Future<OWLClassExpression> future;
    private ExecutorService service;

    InMemDLLearnerJob(ExecutorService service,
                      Future<OWLClassExpression> future) {
        this.future = future;
        this.service = service;
    }

    @Override
    public OWLClassExpression queryResult() {
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }finally{
            service.shutdown();
        }
    }


    public static class JobCallable implements Callable<OWLClassExpression>{

        private IDLConfiguration config;

        public JobCallable(IDLConfiguration config) {
            this.config = config;
        }


        @Override
        public OWLClassExpression call() throws Exception {

            AbstractReasonerComponent reasoner = null;

            for(KnowledgeSource ks: config.getSources()){
                if(ks instanceof SparqlEndpointKS || ks instanceof SparqlKnowledgeSource) {
                    reasoner = new SPARQLReasoner();
                    break;
                }
            }
            if(reasoner == null)
                reasoner = new ClosedWorldReasoner();

            reasoner.setSources(config.getSources());
            reasoner.init();


            AbstractClassExpressionLearningProblem<? extends Score> lp;

            if(config.hasNegativeExamples()) {

                PosNegLPStandard posNeg= new PosNegLPStandard(reasoner);

                posNeg.setPositiveExamples(config.getPositive());

                posNeg.setNegativeExamples(config.getNegative());

                lp = posNeg;

            }else{
                PosOnlyLP pos = new PosOnlyLP(reasoner);

                pos.setPositiveExamples(config.getPositive());

                lp = pos;
            }

            lp.init();

            CELOE alg = new CELOE();
            alg.setMaxExecutionTimeInSeconds(config.getMaxExecutionTime());

            alg.setLearningProblem(lp);
            alg.setReasoner(reasoner);

            alg.init();

            alg.start();

            return alg.getCurrentlyBestDescription();
        }
    }
}
