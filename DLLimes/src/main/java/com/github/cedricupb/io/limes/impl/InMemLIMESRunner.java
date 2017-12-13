package com.github.cedricupb.io.limes.impl;

import com.github.cedricupb.io.limes.ILIMESJob;
import com.github.cedricupb.io.limes.ILIMESRunner;
import org.aksw.limes.core.io.config.Configuration;
import org.aksw.limes.core.io.mapping.AMapping;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InMemLIMESRunner implements ILIMESRunner {
    @Override
    public ILIMESJob execute(Configuration conf) {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Callable<AMapping> call = new InMemLIMESJob.JobCallable(conf);

        return new InMemLIMESJob(service.submit(call));
    }
}
