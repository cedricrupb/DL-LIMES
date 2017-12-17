package com.github.cedricupb.io.limes;

import com.github.cedricupb.io.limes.impl.InMemLIMESRunner;
import org.aksw.limes.core.io.config.Configuration;
import org.aksw.limes.core.io.config.KBInfo;
import org.aksw.limes.core.io.config.reader.xml.XMLConfigurationReader;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ILIMESRunnerDogfood {
    @Test
    public void execute() throws Exception {

        Configuration conf = new Configuration();

        conf.addPrefix("geom", "http://geovocab.org/geometry#");
        conf.addPrefix("geos", "http://www.opengis.net/ont/geosparql#");
        conf.addPrefix("lgdo", "http://linkedgeodata.org/ontology/");
        conf.addPrefix("srwc", "http://swrc.ontoware.org/ontology#");
        conf.addPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        conf.addPrefix("owl", "http://www.w3.org/2002/07/owl#");
        conf.addPrefix("http", "http");

        KBInfo src = new KBInfo();
        src.setPrefixes(conf.getPrefixes());
        src.setId("dogfood");
        src.setType("N3");
        src.setEndpoint("/Users/cedricrichter/Documents/DL-LIMES/DLLimes/src/main/resources/config/dogfood.nt");
        src.setVar("?x");
        src.setPageSize(2000);
        src.setRestrictions(
                new ArrayList<String>(
                        Arrays.asList(new String[]{"?x a srwc:InProceedings"})
                )
        );
        XMLConfigurationReader.processProperty(src, "rdfs:label RENAME label");

        conf.setSourceInfo(src);

        KBInfo target = new KBInfo();
        target.setType("N3");
        target.setPrefixes(conf.getPrefixes());
        target.setId("doogfood");
        target.setEndpoint("/Users/cedricrichter/Documents/DL-LIMES/DLLimes/src/main/resources/config/dogfood.nt");
        target.setVar("?y");
        target.setPageSize(2000);
        target.setRestrictions(
                new ArrayList<String>(
                        Arrays.asList(new String[]{"?x a srwc:InProceedings"})
                )
        );
        XMLConfigurationReader.processProperty(target, "rdfs:label RENAME label");
        conf.setTargetInfo(target);

        conf.setMetricExpression("trigram(x.label, y.label)");

        conf.setAcceptanceFile("doogfood_very_same.nt");
        conf.setAcceptanceThreshold(0.8);
        conf.setAcceptanceRelation("owl:sameAs");

        conf.setVerificationFile("doogfood_near_same.nt");
        conf.setVerificationThreshold(0.5);
        conf.setVerificationRelation("owl:sameAs");

        conf.setExecutionEngine("default");
        conf.setExecutionPlanner("default");
        conf.setExecutionRewriter("default");

        conf.setOutputFormat("TAB");


        ILIMESRunner run = new InMemLIMESRunner();

        System.out.println(
                run.execute(conf).queryResult()
        );

    }

}