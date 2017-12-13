package com.github.cedricupb.lifecycle;

public class Lifecycle implements Runnable {
    protected IPhaseState state;
    protected IPhase actualPhase;

    @Override
    public void run() {
        onStartup();
        while (actualPhase != null) {
            try {
                actualPhase.pushState(state);
                actualPhase.run();
                actualPhase = actualPhase.getNext();
            } catch (Exception e) {
               e.printStackTrace();
               actualPhase = null;
            }
        }
        onShutdown();
    }

    protected void onStartup(){

    }

    protected void onShutdown(){

    }
}
