package com.github.cedricupb.io.limes.impl;

import com.github.cedricupb.io.limes.ILIMESJob;
import org.aksw.limes.core.io.config.Configuration;
import org.aksw.limes.core.io.mapping.AMapping;
import org.aksw.limes.core.io.mapping.reader.RDFMappingReader;
import org.apache.commons.io.FilenameUtils;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cedric Richter on 11.11.17.
 */
public class LocalLIMESJob implements ILIMESJob {

    private Process limesProcess;
    private Configuration conf;
    private String logFile;

    LocalLIMESJob(Process process, Configuration conf, String logFile){
        this.limesProcess = process;
        this.conf = conf;
        this.logFile = logFile;
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

            AMapping map =  loadFile(conf.getAcceptanceFile());
            if(map == null){
               printLog();
            }
            return map;
        }catch(InterruptedException e){
            e.printStackTrace();
        }

        return null;
    }

    private void printLog(){
        Path p = Paths.get(logFile);
        if(Files.exists(p)){
            try {
                Reader reader = new InputStreamReader(
                        Files.newInputStream(p)
                );
                Scanner scanner = new Scanner(reader);
                while(scanner.hasNextLine())
                    System.out.println(scanner.nextLine());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private AMapping loadFile(String path){
        if(Files.exists(Paths.get(path))){
            return new RDFMappingReader(path).read();
        }
        return null;
    }


}
