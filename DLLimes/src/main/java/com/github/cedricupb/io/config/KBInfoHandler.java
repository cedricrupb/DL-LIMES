package com.github.cedricupb.io.config;

import org.aksw.limes.core.io.config.KBInfo;
import org.aksw.limes.core.io.config.reader.xml.XMLConfigurationReader;
import org.xml.sax.Attributes;

import java.util.*;

public class KBInfoHandler implements IXMLEventHandler {
    @Override
    public boolean isHandling(String path, String tag) {
        return path.endsWith("REFINE") && (tag.equals("SOURCE") || tag.equals("TARGET"));
    }

    @Override
    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context) {

        Map<String, String> prefixes;
        if(context.containsKey("prefix")){
            prefixes = (Map<String, String>)context.get("prefix");
        }else{
            prefixes = new HashMap<>();
            context.put("prefix", prefixes);
        }

        KBInfo src = new KBInfo();
        src.setPrefixes(prefixes);
        src.setId((String)childs.get("ID"));
        src.setEndpoint((String)childs.get("ENDPOINT"));
        src.setVar((String)childs.get("VAR"));
        src.setPageSize(Integer.parseInt((String)childs.get("PAGESIZE")));
        if(childs.containsKey("RESTRICTION")) {
            src.setRestrictions(
                    new ArrayList<String>(
                            Arrays.asList(new String[]{(String) childs.get("RESTRICTION")})
                    )
            );
        }
        if(childs.containsKey("PROPERTY")) {
            for(String prop: iterate(childs.get("PROPERTY"), String.class))
                XMLConfigurationReader.processProperty(src, prop);
        }
        if(childs.containsKey("OPTIONAL_PROPERTY")) {
            for(String prop: iterate(childs.get("OPTIONAL_PROPERTY"), String.class))
                XMLConfigurationReader.processOptionalProperty(src, prop);
        }

        if(childs.containsKey("TYPE")){
            src.setType((String)childs.get("TYPE"));
        }

        return src;
    }

    private <T> Iterable<? extends T> iterate(Object o, Class<? extends T> clazz){

        if(o instanceof Iterable){
            return (Iterable<? extends T>)o;
        }

        List<T> list = new ArrayList<>();
        list.add((T)o);
        return list;
    }
}
