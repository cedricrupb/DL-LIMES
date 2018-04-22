package com.github.cedricupb.io.config;

import com.github.cedricupb.Benchmark.Evaluation;
import com.github.cedricupb.lifecycle.IPhaseState;
import com.github.cedricupb.lifecycle.Lifecycle;
import com.github.cedricupb.lifecycle.phases.MappedPhaseState;
import org.junit.Test;

public class DumpFileTest {
    @Test
    public void load() throws Exception {

        XMLConfigLoader loader = new XMLConfigLoader();

        XMLRunningConfiguration config = loader.load("/Users/cedricrichter/Documents/DL-LIMES/DLLimes/src/main/resources/config/dbpedia_dump.xml");

        System.out.println("Load config...");

        IPhaseState state = new MappedPhaseState();
        state.setProperty("configuration", config);

        Lifecycle cycle = new Lifecycle(state);
        cycle.run();
    }

    @Test
    public void testdumpfile(){
        Evaluation qry = new Evaluation();
        qry.queryExecution();
    }
}