package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.io.config.SameReference;
import com.github.cedricupb.lifecycle.IPhase;
import com.github.cedricupb.refine.sourceext.EndPointHelper;
import com.github.cedricupb.refine.sourceext.ISimilaritySet;
import com.github.cedricupb.refine.sourceext.LazyQueryFactory;
import com.github.cedricupb.refine.sourceext.SimpleSimilaritySet;
import org.aksw.limes.core.io.cache.Instance;
import org.aksw.limes.core.io.config.KBInfo;
import org.aksw.limes.core.io.config.reader.xml.XMLConfigurationReader;
import org.aksw.limes.core.io.query.ModelRegistry;
import org.aksw.limes.core.measures.measure.IMeasure;
import org.aksw.limes.core.measures.measure.MeasureFactory;
import org.aksw.limes.core.measures.measure.MeasureType;
import org.aksw.limes.core.measures.measure.string.JaccardMeasure;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.semanticweb.owlapi.model.OWLIndividual;


import java.util.*;

public class SourceExtensionPhase extends APhase {

    private KBInfo source;
    private KBInfo target;

    public SourceExtensionPhase(KBInfo source, KBInfo target, IPhase next) {
        super(next);
        this.source = source;
        this.target = target;
    }

    @Override
    public void run() {

        double threshold  = 0.6;
        double theta = 0.65;

        List<SameReference> positive = (List<SameReference>)state.getProperty("oldPositive");
        List<SameReference> negative = (List<SameReference>)state.getProperty("oldNegative");

        Set<String> srcProp = getCoveredProperties(source, getSourceRestrictions(), threshold);
        Set<String> targetProp = getCoveredProperties(target, getTargetRestrictions(), threshold);

        ISimilaritySet posTestSet = buildTestSet(positive, srcProp, targetProp);
        ISimilaritySet negTestSet = buildTestSet(negative, srcProp, targetProp);

        Map<IMeasure, Double> measures = initMeasureMap();

        Set<String> srcOut = new HashSet<>();
        Set<String> targetOut = new HashSet<>();

        for(Map.Entry<IMeasure, Double> measure: measures.entrySet()){
                for(String src: srcProp){
                    if(srcOut.contains(src))continue;

                    for(String tar: targetProp) {
                        if(targetOut.contains(tar))continue;

                        int posScore = posTestSet.score(src, tar, measure.getKey(), measure.getValue());
                        int negScore = negTestSet.score(src, tar, measure.getKey(), measure.getValue());

                        double f = fmeasure(posScore, negScore, positive.size());

                        if(f >= theta){
                            srcOut.add(src);
                            targetOut.add(tar);
                            break;
                        }

                    }
                }
        }

        if(srcOut.isEmpty()){
            System.out.println("Could not find any properties with theta = "+theta);
        }

        for(String p: srcOut)
            XMLConfigurationReader.processProperty(source, revertPrefix(p, source));

        for(String p: targetOut)
            XMLConfigurationReader.processProperty(target, revertPrefix(p, target));


    }

    //TODO: Dirty walkaround. Please remove after bug fix #135
    private String revertPrefix(String p, KBInfo info){
        for(Map.Entry<String, String> prefix: info.getPrefixes().entrySet()){
            if(p.startsWith(prefix.getValue())){
                return p.replace(prefix.getValue(), prefix.getKey()+":");
            }
        }
        return p;
    }

    private double fmeasure(int posScore, int negScore, int allPos){

        if(posScore == 0)return 0.0;

        double precision = (double)posScore / (posScore + negScore);
        double recall = (double)posScore / allPos;

        return 2 * (precision * recall) / (precision + recall);
    }


    private Map<IMeasure, Double> initMeasureMap(){
        Map<IMeasure, Double> map = new HashMap<>();

        map.put(MeasureFactory.createMeasure(MeasureType.JACCARD), 0.6);
        map.put(MeasureFactory.createMeasure(MeasureType.LEVENSHTEIN), 0.7);

        return map;
    }


    private ISimilaritySet buildTestSet(List<SameReference> list, Set<String> srcProp, Set<String> targetProp){
        List<OWLIndividual> srcI = new ArrayList<>();
        List<OWLIndividual> tarI = new ArrayList<>();

        for(SameReference r: list){
            srcI.add(r.getSource());
            tarI.add(r.getTarget());
        }

        List<Instance> src = createInstances(source, srcI, srcProp);
        List<Instance> tar = createInstances(target, tarI, targetProp);

        return new SimpleSimilaritySet(src, tar);
    }

    private List<Instance> createInstances(KBInfo kb, List<OWLIndividual> ind, Set<String> prop){

        Map<String, Instance> map = new HashMap<>();

        for(OWLIndividual i: ind){
            map.put(i.toStringID(), new Instance(i.toStringID()));
        }

        List<String> keys = new ArrayList<>(map.keySet());

        LazyQueryFactory factory = new LazyQueryFactory();

        String query = EndPointHelper.genPropertyQuery("?x", "?prop", "?z",  keys, prop);

        try {
            for (QuerySolution sol : factory.create(kb, query)) {
                Resource inst = sol.getResource("?x");

                Instance instance = map.get(inst.getURI());

                if(instance == null)continue;

                Resource property = sol.getResource("?prop");
                RDFNode node = sol.get("?z");

                instance.addProperty(property.getURI(), node.toString());



            }
        }finally{
            factory.close();
        }

        return new ArrayList<>(map.values());
    }


    private Set<String> getCoveredProperties(KBInfo kb, List<String> restrictions, double threshold){

        Set<String> prop = new HashSet<>();

        if(!kb.getProperties().isEmpty()){
            prop.addAll(kb.getProperties());
            return prop;
        }

        LazyQueryFactory factory = new LazyQueryFactory();

        String res = createRestriction(restrictions);
        String queryFull = createCountQuery(kb.getVar(), res);

        long count = 0;
        try {

            for (QuerySolution solution : factory.create(kb, queryFull)) {
                count = solution.getLiteral("?c").getLong();
            }

            String cov = createCoverageQuery(kb.getVar(), res);
            for (QuerySolution covSol : factory.create(kb, cov)) {
                Resource r = covSol.getResource("?p");
                long c = covSol.getLiteral("?count").getLong();

                double coverage = c / count;

                if (coverage < threshold) {
                    break;
                }

                prop.add(r.getURI());
            }

        }finally{
            factory.close();
        }

        return prop;
    }

    private List<String> getSourceRestrictions(){
        if(source.getRestrictions().isEmpty()){
            String srcRes = (String)state.getProperty("srcClass");
            return Arrays.asList(new String[]{srcRes});
        }
        return source.getRestrictions();
    }

    private List<String> getTargetRestrictions(){
        if(target.getRestrictions().isEmpty()){
            String targetRes = (String)state.getProperty("targetClass");
            return Arrays.asList(new String[]{targetRes});
        }
        return target.getRestrictions();
    }

    private String createRestriction(List<String> restrictions){
        String query = "", where;

        for (int i = 0; i < restrictions.size(); i++) {
            where = restrictions.get(i).trim();
            if (where.length() > 3) {
                query = query + where + " .\n";
            }
        }

        return query;
    }


    private String createCoverageQuery(String var, String res){
        return "SELECT DISTINCT ?p (count(?p) AS ?count) WHERE {"+res+" "+var+" ?p [].}" +
                " GROUP BY ?p ORDER BY DESC(?count)";
    }

    private String createCountQuery(String var, String res){
        return "SELECT DISTINCT (count("+var+") AS ?c) WHERE {"+res+"}";
    }

}
