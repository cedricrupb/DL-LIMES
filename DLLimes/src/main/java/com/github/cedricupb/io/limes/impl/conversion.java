package com.github.cedricupb.io.limes.impl;

import com.github.cedricupb.config.dllearner.DLConfigBuilder;
import com.github.cedricupb.config.dllearner.IDLConfiguration;
import com.github.cedricupb.io.dllearner.IDLLearnerJob;
import com.github.cedricupb.io.dllearner.IDLLearnerRunner;
import com.github.cedricupb.io.dllearner.inmem.InMemDLLearnerRunner;
import com.github.cedricupb.io.limes.IConversion;
import org.dllearner.kb.OWLFile;
import org.dllearner.utilities.owl.OWLClassExpressionToSPARQLConverter;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import uk.ac.manchester.cs.owl.owlapi.OWLNamedIndividualImpl;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class conversion implements IConversion {

    private String query = null;
    private String restrictions = null;
    private String rootVariable ="?x";
    private OWLClassExpression expr;


// running the dl learner to get the OWL class expression.
    public void dlExecute() throws Exception {
        String uriPrefix = "http://example.com/father#";

        OWLFile ks = new OWLFile();
        ks.setFileName("C:\\Users\\Bilal\\Documents\\GitHub\\PG_DL\\DL-LIMES\\DLLimes\\src\\test\\java\\com\\github\\cedricupb\\io\\dllearner\\inmem\\father.owl");
        ks.init();

        IDLConfiguration config = DLConfigBuilder.init()
                .addKnowledgeSource(ks)
                .addPositive(new OWLNamedIndividualImpl(IRI.create(uriPrefix + "stefan")))
                .addPositive(new OWLNamedIndividualImpl(IRI.create(uriPrefix + "markus")))
                .addPositive(new OWLNamedIndividualImpl(IRI.create(uriPrefix + "martin")))
                .addNegative(new OWLNamedIndividualImpl(IRI.create(uriPrefix + "heinz")))
                .addNegative(new OWLNamedIndividualImpl(IRI.create(uriPrefix + "anna")))
                .addNegative(new OWLNamedIndividualImpl(IRI.create(uriPrefix + "michelle")))
                .setMaxExecutionTimeInSeconds(1)
                .build();

        IDLLearnerRunner runner = new InMemDLLearnerRunner();
        IDLLearnerJob job = runner.execute(config);

        expr = job.queryResult();

        //System.out.println(expr);


    }

    //to convert the OWL class expression into SPARQL quesry using the DL-Learner class.

    @Override
    public String owlTOSparql(OWLClassExpression ce) {
        OWLClassExpressionToSPARQLConverter converter = new OWLClassExpressionToSPARQLConverter();

        query = String.valueOf(converter.asQuery(rootVariable, ce));
       // System.out.println(query);
        qrestrictions(query);

        return query;
    }


    // Extracting the where clause(rules) from the SPARQL query so that they can be passed to Limes.


    @Override
    public String qrestrictions(String qry) {

        String str = qry;

        final Matcher matcher = Pattern.compile("WHERE").matcher(str);

        if(matcher.find()){

            restrictions = (str.substring(matcher.end()).trim());
        }

        System.out.println(restrictions);
        return restrictions;


    }
}
