package com.github.cedricupb.Benchmark;


import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfxml.xmloutput.impl.Abbreviated;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

import java.util.List;

public class Evaluation {


    /*Variables that can be accessed in the class*/
   // private String qpram = "dbpedia.org/ontology";

    List<String> resourselist = new ArrayList<>(); // to save the resources of the class

    private String[] classes =
            {"Publisher", "School", "Abbey", "AcademicConference",
                    "AcademicJournal", "AcademicSubject", "AdultActor"
                    ,"Agglomeration", "Airline", "Airport"};


    public void queryExecution() {

        // to get the describtion of the each recource of The Class.
       for (int i=0;i<classes.length;i++){
            if (classes[i]!= null)
                getResources(classes[i]);

                            for (int j=0; j<resourselist.size();j++){
                               //if (resourselist.get(j)!= " ") //&& (resourselist.get(j).startsWith("_")))
                              getResourceDescription(resourselist.get(j));
                                   //System.out.println(resourselist.get(j));
                            }

        }

    }

    /*Method to get the description of the classes*/
    public void getResources(String cname) {


        String qstring =
                "PREFIX dbo: <http://dbpedia.org/ontology/>" +
                        "Select Distinct ?r where {" +
                        "?r a dbo:"+cname+" ." +
                        "}" +
                        "limit 100";

        Query resource_query = QueryFactory.create(qstring);
        QueryExecution Exe =
                QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", resource_query);

        try {
            ResultSet resource_results = Exe.execSelect();
            for ( ; resource_results.hasNext() ; )
            {
                QuerySolution soln = resource_results.nextSolution() ;
                RDFNode a = soln.get("r") ;
                String result = a.asNode()+"";
                //System.out.println(result);
                //String res = result.replaceAll("\\s",""); // trying to remove the blank spaces
                resourselist.add(result);
            }

        } finally {
            Exe.close();
                    }

    } // ending of the GetResources Method

    public void getResourceDescription(String rname){

        String desptstring ="Prefix dbo: <http://dbpedia.org/resource/>\n" +
                "Describe ?r  where{" +
                "    <"+rname+"> ?r ?o." +
                "}";

        Writer writer = null;
        Query des_query = QueryFactory.create(desptstring);
        QueryExecution des_Exe = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", des_query);

        try {
            Model model = des_Exe.execDescribe();
            //model = (Model) set;
            //ResultSet description_result = des_Exe.execSelect();

            StringWriter strwrt = new StringWriter();
            //for ( ; description_result.hasNext() ; ) {
            //QuerySolution res = description_result.nextSolution();

            Abbreviated abb = new Abbreviated();
            abb.write(model, strwrt, null);
            //System.out.println(res);

            File file = new File("D:\\newfile.txt");
            file.createNewFile();
            Files.write(Paths.get("D:\\newfile.txt"), strwrt.toString().getBytes(), StandardOpenOption.APPEND);
            // }

        }

        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            des_Exe.close();
        }




    } // ending of the GetResourceDescription Method

} // class Ending
