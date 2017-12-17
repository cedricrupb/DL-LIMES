package com.github.cedricupb.io.config;

import com.github.cedricupb.io.dllearner.IDLLearnerRunner;
import com.github.cedricupb.io.limes.ILIMESRunner;

public class XMLRunningConfiguration {

    private ILIMESRunner limes;
    private IDLLearnerRunner dllearner;

    private XMLRefineConfiguration refine;
    private MLConfig mlConfig;

    private TerminateConfig terminate;

    public XMLRunningConfiguration(ILIMESRunner limes, IDLLearnerRunner dllearner,
                                   XMLRefineConfiguration refine, TerminateConfig terminate,
                                   MLConfig config) {
        this.limes = limes;
        this.dllearner = dllearner;
        this.refine = refine;
        this.terminate = terminate;
        this.mlConfig = config;
    }

    public ILIMESRunner getLimes() {
        return limes;
    }

    public IDLLearnerRunner getDllearner() {
        return dllearner;
    }

    public XMLRefineConfiguration getRefine() {
        return refine;
    }

    public MLConfig getMlConfig() {
        return mlConfig;
    }

    public TerminateConfig getTerminate() {
        return terminate;
    }


}
