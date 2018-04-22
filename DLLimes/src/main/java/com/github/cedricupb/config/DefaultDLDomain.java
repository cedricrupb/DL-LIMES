package com.github.cedricupb.config;

import com.github.cedricupb.io.limes.ILIMESRunner;
import org.apache.commons.cli.Options;

public class DefaultDLDomain implements IDLDomain {

    private Options options;

    // Defining the command line option of the dl learner
    @Override
    public Options getCLIOptions() {
        if(options == null){
            options = new Options();

            options.addOption("c", true, "Configuration file for DLLimes Interfaces");
        }

        return options;
    }

    @Override
    public ILIMESRunner getLimesInterface() {
        return null;
    }
}
