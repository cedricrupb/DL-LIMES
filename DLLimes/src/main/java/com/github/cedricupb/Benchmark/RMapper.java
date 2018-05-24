package com.github.cedricupb.Benchmark;

import java.io.*;
import java.util.*;

public class RMapper {
    private ArrayList<String> listResource;
    //List<String> listResource = new ArrayList<String>();
    private List<String> listTargetResource;
    java.util.Map<Integer,Map> source;
    java.util.Map<Integer,Map> target;
    //String file_name;
    //String folder_name;

//    public RMapper() throws IOException {
//    }

    public List<String> readSourcefile() throws IOException {
        File file = new File("D:dumpfile1.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        //String line = br.readLine();

        String st;
        while ((st = br.readLine()) != null)
            if (st.contains("rdf:resource")) {

                //System.out.println(st.substring(st.indexOf("=") + 2, st.lastIndexOf(">") - 2));
                listResource.add(st.substring(st.indexOf("=") + 2, st.lastIndexOf(">") - 2));


            }
        //System.out.println(st);
        return listResource;

    }

    public void matcher() throws IOException{

        List<String> sourceResources = readSourcefile();
        listTargetResource = readSourcefile();
        List<String> targetResources ;
        Iterator<String> i = sourceResources.iterator();

        while(i.hasNext()){
            Random rand = new Random();
            Map map = new Map(rand.nextInt(50),i.next());
            source.put(map.getKey(),map);
            target.put(rand.nextInt(50),map);

            Set<String> intersect = new HashSet<String>((HashSet)source.entrySet());
            intersect.retainAll(target.entrySet());
            Iterator itr = intersect.iterator();
            while(itr.hasNext())
            {
                System.out.println(itr.next());
            }


        }

    }

    class Map{
        Integer key;
        String value;

        public Map(Integer key, String value) {
            this.key = key.hashCode();
            this.value = value;
        }
        public int hashCode(){
            int count = 13;
            return count * key;
        }

        public Integer getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }


}