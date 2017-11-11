package com.github.cedricupb.io.limes.impl;

import com.github.cedricupb.io.limes.ILIMESJob;
import com.github.cedricupb.io.limes.ILIMESRunner;
import org.aksw.limes.core.io.config.Configuration;
import org.aksw.limes.core.io.config.writer.IConfigurationWriter;
import org.aksw.limes.core.io.config.writer.XMLConfigurationWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Created by Cedric Richter on 11.11.17.
 */
public class LocalLIMESRunner implements ILIMESRunner {

    private static final String TMP_DIR_PREFIX = "dllimes/limes";

    private enum ConfType{
        XML,RDF
    }

    private IConfigurationWriter writer;
    private String javaHome;
    private String limesJar;
    private ConfType type;

    public LocalLIMESRunner(IConfigurationWriter writer, String javaHome, String limesJar) {
        this.writer = writer;
        this.javaHome = javaHome;
        this.limesJar = limesJar;
    }

    public ILIMESJob execute(Configuration conf) {
        try {
            Path tmpDir = Files.createTempDirectory(TMP_DIR_PREFIX);

            conf = sandBoxConf(tmpDir, flatCopyConf(conf));

            String confFile = writeConf(tmpDir, conf);


            String format = getFormatString();
            String logging = tmpDir.toString()+"/logging_"+conf.hashCode()+".log";

            ProcessBuilder builder = new ProcessBuilder(
                    javaHome, "-jar", limesJar, confFile, "-f", format, "-o",logging
            );

            return new LocalLIMESJob(builder.start(), conf);

        }catch(IOException e){

        }
        return null;
    }

    private ConfType getConfType(){
        if(type == null){
            type = (writer instanceof XMLConfigurationWriter)?ConfType.XML:ConfType.RDF;
        }
        return type;
    }

    private String selectExt(){
        switch(getConfType()){
            case RDF:
                return "rdf";
            default:
                return "xml";
        }
    }

    private String getFormatString(){
        switch(getConfType()){
            case RDF:
                return "RDF";
            default:
                return "XML";
        }
    }

    private String writeConf(Path dir, Configuration conf) throws IOException{
        String outFile = "config_"+conf.hashCode()+"."+selectExt();
        writer.write(conf,dir.toString()+"/"+outFile);
        return outFile;
    }

    private Configuration flatCopyConf(Configuration conf){
        return new Configuration(conf.getSourceInfo(), conf.getTargetInfo(), conf.getMetricExpression(),
                conf.getAcceptanceRelation(), conf.getVerificationRelation(), conf.getAcceptanceThreshold(),
                conf.getAcceptanceFile(), conf.getVerificationThreshold(), conf.getVerificationFile(),
                conf.getPrefixes(), conf.getOutputFormat(), conf.getExecutionRewriter(), conf.getExecutionPlanner(),
                conf.getExecutionEngine(), conf.getGranularity(), conf.getMlAlgorithmName(), conf.getMlAlgorithmParameters(),
                conf.getMlImplementationType(), conf.getMlTrainingDataFile(), conf.getMlPseudoFMeasure());
    }

    private Configuration sandBoxConf(Path dir, Configuration conf){
        Path tmp = Paths.get(conf.getAcceptanceFile());
        conf.setAcceptanceFile(dir.resolve(tmp).toString());
        tmp = Paths.get(conf.getVerificationFile());
        conf.setVerificationFile(dir.resolve(tmp).toString());
        return conf;
    }
}
