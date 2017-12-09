package com.github.cedricupb;

import com.github.cedricupb.config.ConfigLoadedDLDomain;
import com.github.cedricupb.config.DefaultDLDomain;
import com.github.cedricupb.config.IDLDomain;
import org.apache.commons.cli.*;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class DLLimes {


    public static void main(String[] args){
        IDLDomain domain = onStartup(args);
        while (run(domain)) {

        }
        onShutdown(domain);
    }

    private static void printHelp(IDLDomain domain){

    }

    private static IDLDomain onStartup(String[] args) {
        IDLDomain domain = new DefaultDLDomain();
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(domain.getCLIOptions(), args);
        } catch (ParseException e) {
            printHelp(domain);
        }

        if(cmd.hasOption("c")){
            try {
                domain = new ConfigLoadedDLDomain(domain, cmd.getOptionValue("c"));
            }catch(ConfigurationException e){
                System.out.println(cmd.getOptionValue("c")+" is not loadable.");
            }
        }


        return domain;

    }

    private static boolean run(IDLDomain domain){
        return false;
    }

    private static void onShutdown(IDLDomain domain){

    }

}
