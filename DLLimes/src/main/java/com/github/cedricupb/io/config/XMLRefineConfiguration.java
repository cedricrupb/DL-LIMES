package com.github.cedricupb.io.config;

import org.aksw.limes.core.io.config.KBInfo;


public class XMLRefineConfiguration {

    private KBInfo source;
    private KBInfo target;

    private XMLExampleConfiguration examples;

    public XMLRefineConfiguration(KBInfo source, KBInfo target, XMLExampleConfiguration examples) {
        this.source = source;
        this.target = target;
        this.examples = examples;
    }

    public KBInfo getSource() {
        return source;
    }

    public KBInfo getTarget() {
        return target;
    }

    public XMLExampleConfiguration getExamples() {
        return examples;
    }


}
