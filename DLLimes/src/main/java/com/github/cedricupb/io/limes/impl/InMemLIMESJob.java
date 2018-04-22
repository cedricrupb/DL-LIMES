package com.github.cedricupb.io.limes.impl;


import com.github.cedricupb.io.limes.ILIMESJob;
import org.aksw.limes.core.controller.Controller;
import org.aksw.limes.core.io.config.Configuration;
import org.aksw.limes.core.io.mapping.AMapping;


import java.util.concurrent.*;

// The class run in the Limes using the configuration.
public class InMemLIMESJob implements ILIMESJob {

    private Future<AMapping> future;
    private ExecutorService service;

    InMemLIMESJob(ExecutorService service, Future<AMapping> future) {
        this.service = service;
        this.future = future;
    }

    @Override
    public AMapping queryResult() {
        try {
            return this.future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally{
            service.shutdown();
        }
        return null;
    }

    // The method will call the query result. The method will wait for specified time and then call the results.
    @Override
    public AMapping queryResult(long timeout, TimeUnit unit) {
        try {
            return this.future.get(timeout, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally{
            service.shutdown();
        }
        return null;
    }

    public static class JobCallable implements Callable<AMapping> {

        private Configuration config;

        //
        public JobCallable(Configuration config) {
            this.config = config;
        }


        //
        @Override
        public AMapping call() throws Exception {
            return Controller.getMapping(config).getAcceptanceMapping();
        }
    }
}
