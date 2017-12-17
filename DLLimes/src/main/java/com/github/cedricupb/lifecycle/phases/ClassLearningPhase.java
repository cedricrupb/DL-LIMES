package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.config.dllearner.DLConfigBuilder;
import com.github.cedricupb.config.dllearner.IDLConfiguration;
import com.github.cedricupb.io.config.SameReference;
import com.github.cedricupb.io.config.XMLRunningConfiguration;
import com.github.cedricupb.io.dllearner.IDLLearnerJob;
import com.github.cedricupb.io.dllearner.IDLLearnerRunner;
import com.github.cedricupb.io.dllearner.inmem.InMemDLLearnerRunner;
import com.github.cedricupb.lifecycle.IPhase;
import com.github.cedricupb.lifecycle.IPhaseState;
import org.aksw.limes.core.io.config.KBInfo;
import org.dllearner.core.ComponentInitException;
import org.dllearner.core.KnowledgeSource;
import org.dllearner.kb.OWLFile;
import org.dllearner.kb.SparqlEndpointKS;
import org.semanticweb.owlapi.model.OWLIndividual;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ClassLearningPhase implements IPhase {

    private IPhaseState state;
    private IPhase next;
    private boolean running = false;

    public ClassLearningPhase(IPhase next) {
        this.next = new Class2RestrictionPhase(next);
    }

    @Override
    public void pushState(IPhaseState state) {
        this.state = state;
    }

    @Override
    public IPhase getNext() {
        return next;
    }

    @Override
    public boolean isPhaseRunning() {
        return running;
    }

    @Override
    public void onShutdown() {

    }

    @Override
    public void run() {
        running = true;
        System.out.println("(Iteration "+state.getProperty("iterations")+") Start class learning...");

        XMLRunningConfiguration config = (XMLRunningConfiguration)state.getProperty("configuration");

        List<SameReference> positive = (List<SameReference>)state.getProperty("positiveExamples");
        state.setProperty("positiveExamples", null);

        if(positive == null){
            throw new NullPointerException("Positive examples have to be set!");
        }

        state.setProperty("oldPositive", positive);

        List<SameReference> negative = (List<SameReference>)state.getProperty("negativeExamples");
        state.setProperty("negativeExamples", null);

        if(negative == null) {
            negative = (List<SameReference>)state.getProperty("oldNegative");

            if(negative == null){
                negative = new ArrayList<>();
            }
        }

        state.setProperty("oldNegative", negative);

        List<OWLIndividual> srcPos = new ArrayList<>(),
                srcNeg = new ArrayList<>(),
                tarPos = new ArrayList<>(),
                tarNeg = new ArrayList<>();

        for(SameReference ref: positive){
            srcPos.add(ref.getSource());
            tarPos.add(ref.getTarget());
        }

        for(SameReference ref: negative){
            srcNeg.add(ref.getSource());
            tarNeg.add(ref.getTarget());
        }

        IDLConfiguration dlConfig;
        try {
            dlConfig = DLConfigBuilder.init()
                    .addKnowledgeSource(transform(config.getRefine().getSource()))
                    .addPositives(srcPos)
                    .addNegatives(srcNeg)
                    .setMaxExecutionTimeInSeconds(2)
                    .build();


            IDLLearnerJob srcJob = config.getDllearner().execute(dlConfig);

            state.setProperty("srcClass", srcJob.queryResult());

            System.out.println("(Iteration "+state.getProperty("iterations")+") Source class expression: "
                    + state.getProperty("srcClass"));

            dlConfig = DLConfigBuilder.init()
                    .addKnowledgeSource(transform(config.getRefine().getTarget()))
                    .addPositives(tarPos)
                    .addNegatives(tarNeg)
                    .setMaxExecutionTimeInSeconds(2)
                    .build();

            IDLLearnerJob tarJob = config.getDllearner().execute(dlConfig);

            state.setProperty("targetClass", tarJob.queryResult());


            System.out.println("(Iteration "+state.getProperty("iterations")+") Target class expression: "
                    + state.getProperty("targetClass"));

        } catch (ComponentInitException e) {
            e.printStackTrace();
            next = null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            next = null;
        }

        running = false;
    }

    private KnowledgeSource transform(KBInfo info) throws ComponentInitException, MalformedURLException {
        if(Files.exists(Paths.get(info.getEndpoint()))){
            OWLFile ks = new OWLFile();
            ks.setFileName(info.getEndpoint());
            ks.init();
            return ks;
        }else{
            SparqlEndpointKS ks = new SparqlEndpointKS();
            ks.setUrl(new URL(info.getEndpoint()));
            ks.init();
            return ks;
        }
    }
}
