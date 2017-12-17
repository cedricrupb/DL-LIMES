package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.lifecycle.IPhase;
import com.github.cedricupb.lifecycle.IPhaseState;

public abstract class APhase implements IPhase {

    protected IPhase next;
    protected IPhaseState state;
    protected boolean running = false;

    public APhase(IPhase next){
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
        running = false;
    }

}
