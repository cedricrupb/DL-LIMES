package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.io.config.XMLRunningConfiguration;
import com.github.cedricupb.lifecycle.IPhase;

public class ExtendingBranchPhase extends APhase{

    public ExtendingBranchPhase(IPhase next) {
        super(next);
    }

    @Override
    public void run() {

        running = true;

        XMLRunningConfiguration config = (XMLRunningConfiguration) state.getProperty("configuration");

        if(config.getRefine().getSource().getProperties().isEmpty() ||
                config.getRefine().getTarget().getProperties().isEmpty()){
            next = new SourceExtensionPhase(config.getRefine().getSource(),
                    config.getRefine().getTarget(),
                    next);
        }else if(next instanceof SourceExtensionPhase) {
            next = next.getNext();
        }

        running = false;
    }
}
