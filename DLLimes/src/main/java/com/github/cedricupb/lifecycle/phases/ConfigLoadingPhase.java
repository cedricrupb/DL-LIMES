package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.io.config.XMLConfigLoader;
import com.github.cedricupb.io.config.XMLRunningConfiguration;
import com.github.cedricupb.lifecycle.IPhase;
import com.github.cedricupb.lifecycle.IPhaseState;


public class ConfigLoadingPhase implements IPhase {

    private IPhase next;
    private boolean running = false;
    private IPhaseState state;

    public ConfigLoadingPhase(IPhase next) {
        this.next = next;
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

        System.out.println("Start loading configuration...");

        XMLRunningConfiguration config;
        String confFile = "";

        Object confObj = state.getProperty("configuration");

        if(confObj instanceof XMLRunningConfiguration){
            config = (XMLRunningConfiguration)confObj;
        }else {
            confFile = (String) state.getProperty("configuration");
            XMLConfigLoader loader = new XMLConfigLoader();
            config = loader.load(confFile);
        }


        state.setProperty("configuration", config);

        if(state.getProperty("configuration") == null){
            System.out.println("Cannot load configuration file"+(confFile.isEmpty()?".":": "+confFile));
            next = null;
        }

        running = false;
    }
}
