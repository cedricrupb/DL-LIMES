package com.github.cedricupb.lifecycle.phases;

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

        running = false;
    }
}
