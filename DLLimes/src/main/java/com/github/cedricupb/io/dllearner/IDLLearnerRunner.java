package com.github.cedricupb.io.dllearner;

import com.github.cedricupb.config.dllearner.IDLConfiguration;


public interface IDLLearnerRunner {

    public IDLLearnerJob execute(IDLConfiguration config);

}
