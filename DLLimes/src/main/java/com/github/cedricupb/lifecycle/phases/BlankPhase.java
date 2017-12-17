package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.lifecycle.IPhase;
import com.github.cedricupb.lifecycle.IPhaseState;

public class BlankPhase implements IPhase {

    private IPhase wrapping;

    public void setWrapping(IPhase wrapping) {
        this.wrapping = wrapping;
    }


    @Override
    public void pushState(IPhaseState state) {
        if(wrapping != null)
            wrapping.pushState(state);
    }

    @Override
    public IPhase getNext() {
        if(wrapping != null){
            return wrapping.getNext();
        }
        return null;
    }

    @Override
    public boolean isPhaseRunning() {
        return wrapping != null ? wrapping.isPhaseRunning(): false;
    }

    @Override
    public void onShutdown() {
        if(wrapping != null)
            wrapping.onShutdown();
    }

    @Override
    public void run() {
        if(wrapping != null)
            wrapping.run();
    }
}
