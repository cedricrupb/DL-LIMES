package com.github.cedricupb.io.limes.impl;

import com.github.cedricupb.io.limes.ILIMESJob;
import org.aksw.limes.core.io.config.Configuration;
import org.aksw.limes.core.io.mapping.AMapping;
import org.aksw.limes.core.io.mapping.reader.RDFMappingReader;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cedric Richter on 11.11.17.
 */
public class LocalLIMESJob implements ILIMESJob {

    private Process limesProcess;
    private Configuration conf;

    LocalLIMESJob(Process process, Configuration conf){
        this.limesProcess = process;
        this.conf = conf;
    }

    @Override
    public AMapping queryResult() {
        return queryResult(-1, TimeUnit.MILLISECONDS);
    }

    @Override
    public AMapping queryResult(long timeout, TimeUnit unit) {
        try {
            if (timeout > 0) {
                limesProcess.waitFor(timeout, unit);
            }else{
                limesProcess.waitFor();
            }

            return loadFile(conf.getAcceptanceFile());
        }catch(InterruptedException e){
        }

        return null;
    }

    private AMapping loadFile(String path){
        if(Files.exists(Paths.get(path))){
            return new RDFMappingReader(path).read();
        }
        return null;
    }


}
