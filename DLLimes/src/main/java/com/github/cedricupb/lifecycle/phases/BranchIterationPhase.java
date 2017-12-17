package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.io.config.SameReference;
import com.github.cedricupb.io.config.XMLRunningConfiguration;
import com.github.cedricupb.lifecycle.IPhase;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BranchIterationPhase extends APhase {

    private IPhase recursion;
    private IPhase branch;

    public BranchIterationPhase(IPhase recursion, IPhase next) {
        super(next);
        this.branch = this.next;
        this.recursion = recursion;
    }

    @Override
    public void run() {
        running = true;

        XMLRunningConfiguration config = (XMLRunningConfiguration)state.getProperty("configuration");

        int it = (int)state.getProperty("iterations")+1;

        if(it >= config.getTerminate().getIteration()){
            this.next = this.branch;
            return;
        }

        List<SameReference> nPos = (List<SameReference>) state.getProperty("positiveExamples");
        List<SameReference> oldPos = (List<SameReference>) state.getProperty("oldPositive");

        if(config.getTerminate().isFixpoint() && isFixpoint(oldPos, nPos)){
            this.next = this.branch;
            return;
        }

        state.setProperty("iterations", it);
        super.next = recursion;

        running = false;
    }

    private boolean isFixpoint(List<SameReference> old, List<SameReference> n){
        Set<SameReference> oldS = new HashSet<>(old);

        for(SameReference ref: n){
            if(!oldS.remove(ref))
                return false;
        }

        return oldS.isEmpty();
    }
}
