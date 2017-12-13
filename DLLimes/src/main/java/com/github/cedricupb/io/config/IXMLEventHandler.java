package com.github.cedricupb.io.config;

import org.xml.sax.Attributes;

import javax.xml.stream.events.XMLEvent;
import java.util.Map;

public interface IXMLEventHandler {

    public boolean isHandling(String path, String tag);

    public Object handle(String tag, Attributes attr, Map<String, Object> childs, Map<String, Object> context);

}
