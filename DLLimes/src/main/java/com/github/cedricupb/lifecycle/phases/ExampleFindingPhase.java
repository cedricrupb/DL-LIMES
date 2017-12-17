package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.io.config.MLConfig;
import com.github.cedricupb.io.config.XMLRunningConfiguration;
import com.github.cedricupb.io.limes.ILIMESJob;
import com.github.cedricupb.lifecycle.IPhase;
import org.aksw.limes.core.io.config.Configuration;
import org.aksw.limes.core.ml.algorithm.MLImplementationType;

public class ExampleFindingPhase extends APhase {

    public ExampleFindingPhase(IPhase next) {
        super(new Mapping2SameRefPhase(next));
    }

    @Override
    public void run() {

        System.out.println("(Iteration "+state.getProperty("iterations")+") Start example finding...");

        running = true;

        XMLRunningConfiguration config = (XMLRunningConfiguration)state.getProperty("configuration");

        Configuration limesConfig = new Configuration();

        limesConfig.getPrefixes().putAll(config.getRefine().getSource().getPrefixes());
        limesConfig.setSourceInfo(config.getRefine().getSource());
        limesConfig.setTargetInfo(config.getRefine().getTarget());

        MLConfig ml = config.getMlConfig();

        limesConfig.setMlAlgorithmName(ml.getMlAlgorithmName());
        limesConfig.setMlImplementationType(ml.getMlImplementationType());
        limesConfig.setMlAlgorithmParameters(ml.getMlAlgorithmParameters());

        if(!ml.getMlTrainingDataFile().isEmpty()){
            limesConfig.setTrainingDataFile(ml.getMlTrainingDataFile());
        }

        limesConfig.setAcceptanceFile("wombat_simple_same.nt");
        limesConfig.setAcceptanceThreshold(ml.getThreshold());
        limesConfig.setAcceptanceRelation("<http://www.w3.org/2002/07/owl#sameAs>");

        limesConfig.setVerificationFile("wombat_simple_review.nt");
        limesConfig.setVerificationThreshold(0.5);
        limesConfig.setVerificationRelation("<http://www.w3.org/2002/07/owl#sameAs>");

        limesConfig.setExecutionEngine("default");
        limesConfig.setExecutionPlanner("default");
        limesConfig.setExecutionRewriter("default");

        limesConfig.setOutputFormat("TAB");

        ILIMESJob exampleJob = config.getLimes().execute(limesConfig);
        state.setProperty("sameMapping", exampleJob.queryResult());

        running = false;
    }
}
