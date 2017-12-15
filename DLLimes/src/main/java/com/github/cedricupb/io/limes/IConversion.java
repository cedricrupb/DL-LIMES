package com.github.cedricupb.io.limes;


import org.semanticweb.owlapi.model.OWLClassExpression;

public interface IConversion {

    String owlTOSparql (OWLClassExpression ce);
    String qrestrictions(String qry);

}