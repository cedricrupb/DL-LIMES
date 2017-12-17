package com.github.cedricupb;

import com.github.cedricupb.config.ConfigLoadedDLDomain;
import com.github.cedricupb.config.DefaultDLDomain;
import com.github.cedricupb.config.IDLDomain;
import com.github.cedricupb.lifecycle.IPhaseState;
import com.github.cedricupb.lifecycle.Lifecycle;
import com.github.cedricupb.lifecycle.phases.MappedPhaseState;
import org.apache.commons.cli.*;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class DLLimes {


    public static void main(String[] args){
        Options options = getCLIOptions();
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            printHelp(options);
        }

        if(cmd.hasOption("c")){
            String file = cmd.getOptionValue("c");
            IPhaseState state = new MappedPhaseState();
            state.setProperty("configuration", file);

            Lifecycle cycle = new Lifecycle(state);
            cycle.run();

        }

    }

    private static void printHelp(Options options){

    }


    private static Options getCLIOptions() {

        Options options = new Options();

        options.addOption("c", true, "Configuration file for DLLimes Interfaces");

        return options;
    }



}
