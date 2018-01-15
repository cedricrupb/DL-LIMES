package com.github.cedricupb.lifecycle;

import com.github.cedricupb.lifecycle.phases.*;

public class Lifecycle implements Runnable {

    protected IPhaseState state;
    protected IPhase actualPhase;

    public Lifecycle(IPhaseState state) {
        this.state = state;
    }

    public Lifecycle() {
        this(new MappedPhaseState());
    }

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
        state.setProperty("iterations", 0);

        BlankPhase recursionPoint = new BlankPhase();

        actualPhase = new ConfigLoadingPhase(
                new FirstIterationSetupPhase(
                        recursionPoint
                )
        );

        recursionPoint.setWrapping(
                new ClassLearningPhase(
                        new ExtendingBranchPhase(
                            new ExampleFindingPhase(
                                    new BranchIterationPhase(
                                            recursionPoint,
                                            new ResultEmittingPhase()
                                    )
                            )
                        )
                )
        );
    }

    protected void onShutdown(){

    }
}
