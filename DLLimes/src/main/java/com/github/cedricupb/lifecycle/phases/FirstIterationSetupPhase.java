package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.io.config.XMLRunningConfiguration;
import com.github.cedricupb.lifecycle.IPhase;
import com.github.cedricupb.lifecycle.IPhaseState;

public class FirstIterationSetupPhase extends APhase {

    public FirstIterationSetupPhase(IPhase next) {
        super(next);
    }

    @Override
    public void run() {
        running = true;
        System.out.println("Prepare first iteration...");

        XMLRunningConfiguration config = (XMLRunningConfiguration) state.getProperty("configuration");

        state.setProperty("positiveExamples", config.getRefine().getExamples().getPositive());
        state.setProperty("negativeExamples", config.getRefine().getExamples().getNegative());

        running = false;
    }
}
