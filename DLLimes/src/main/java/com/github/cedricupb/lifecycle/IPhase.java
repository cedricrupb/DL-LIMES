package com.github.cedricupb.lifecycle;

public interface IPhase extends Runnable{

    public void pushState(IPhaseState state);

    public IPhase getNext();

    public boolean isPhaseRunning();

    public void onShutdown();

}
