package com.github.cedricupb.io.config;

import com.github.cedricupb.io.dllearner.IDLLearnerRunner;
import com.github.cedricupb.io.limes.ILIMESRunner;

public class XMLRunningConfiguration {

    private ILIMESRunner limes;
    private IDLLearnerRunner dllearner;

    private XMLRefineConfiguration refine;

    public XMLRunningConfiguration(ILIMESRunner limes, IDLLearnerRunner dllearner, XMLRefineConfiguration refine) {
        this.limes = limes;
        this.dllearner = dllearner;
        this.refine = refine;
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


}
