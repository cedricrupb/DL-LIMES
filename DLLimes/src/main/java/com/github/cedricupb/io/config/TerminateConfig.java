package com.github.cedricupb.io.config;

public class TerminateConfig {

    private int iteration = 10;
    private boolean fixpoint = false;
    private String outFile = "same_as.nt";

    public TerminateConfig(int iteration, boolean fixpoint, String out) {
        this.iteration = iteration;
        this.fixpoint = fixpoint;
        this.outFile = out;
    }

    public int getIteration() {
        return iteration;
    }

    public boolean isFixpoint() {
        return fixpoint;
    }

    public String getOutFile() {
        return outFile;
    }

}
