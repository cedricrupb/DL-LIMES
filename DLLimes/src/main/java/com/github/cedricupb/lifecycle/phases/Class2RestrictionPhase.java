package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.io.config.XMLRefineConfiguration;
import com.github.cedricupb.io.config.XMLRunningConfiguration;
import com.github.cedricupb.lifecycle.IPhase;
import org.dllearner.utilities.owl.OWLClassExpressionToSPARQLConverter;
import org.semanticweb.owlapi.model.OWLClassExpression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Class2RestrictionPhase extends APhase {

    public Class2RestrictionPhase(IPhase next) {
        super(next);
    }

    @Override
    public void run() {

        running = true;

        XMLRunningConfiguration config = (XMLRunningConfiguration) state.getProperty("configuration");

        Object o = state.getProperty("srcClass");
        if(o != null && o instanceof OWLClassExpression){
            OWLClassExpression srcExpr = (OWLClassExpression)o;
            String srcWhere = transformExpr(srcExpr, config.getRefine().getSource().getVar());
            state.setProperty("srcClass", srcWhere);
        }

        o = state.getProperty("targetClass");
        if(o != null && o instanceof OWLClassExpression){
            OWLClassExpression targetExpr = (OWLClassExpression)o;
            String targetWhere = transformExpr(targetExpr, config.getRefine().getSource().getVar());
            state.setProperty("targetClass", targetWhere);
        }

        running = false;

    }

    private String transformExpr(OWLClassExpression expr, String rootVar){

        OWLClassExpressionToSPARQLConverter converter = new OWLClassExpressionToSPARQLConverter();

        String str = String.valueOf(converter.asQuery(rootVar, expr));

        final Matcher matcher = Pattern.compile("WHERE").matcher(str);

        String restrictions = "";

        if(matcher.find()){

            restrictions = (str.substring(matcher.end()).trim());
        }else{
            System.out.println("Problem while extracting Where clause: "+str);
        }

        return restrictions;
    }
}
