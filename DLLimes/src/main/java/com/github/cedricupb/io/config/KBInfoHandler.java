package com.github.cedricupb.io.config;

import org.aksw.limes.core.io.config.KBInfo;
import org.aksw.limes.core.io.config.reader.xml.XMLConfigurationReader;
import org.xml.sax.Attributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
        src.setRestrictions(
                new ArrayList<String>(
                        Arrays.asList(new String[]{(String)childs.get("RESTRICTION")})
                )
        );
        if(childs.containsKey("PROPERTY")) {
            XMLConfigurationReader.processProperty(src, (String)childs.get("PROPERTY"));
        }
        if(childs.containsKey("OPTIONAL_PROPERTY")) {
            XMLConfigurationReader.processOptionalProperty(src, (String)childs.get("OPTIONAL_PROPERTY"));
        }

        if(childs.containsKey("TYPE")){
            src.setType((String)childs.get("TYPE"));
        }

        return src;
    }
}
