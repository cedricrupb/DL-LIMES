package com.github.cedricupb.config;

import com.github.cedricupb.io.limes.ILIMESRunner;
import org.apache.commons.cli.Options;


public interface IDLDomain {

    public Options getCLIOptions();

    public ILIMESRunner getLimesInterface();

    //TODO Add DL-Learner interface

}
