package com.github.cedricupb.io.config;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

public class XMLHandler extends DefaultHandler {

    private Stack<ActHandler> handlerStack = new Stack<>();
    private Stack<String> path = new Stack<>();
    private List<IXMLEventHandler> handlers = new ArrayList<>();
    private Map<String, Object> context = new HashMap<>();
    private Object root;

    public List<IXMLEventHandler> getHandlers() {
        return handlers;
    }

    public Object getRootAndReset() {
        Object tmp = root;
        root = null;
        context.clear();
        path.clear();
        handlerStack.clear();
        return tmp;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {

        String p = "";

        for(String s: path){
            p += s+".";
        }
        p = p.length()>0?p.substring(0, p.length()-1):p;


        IXMLEventHandler handler = null;
        for(IXMLEventHandler h: handlers){
            if(h.isHandling(p, qName)){
                handler = h;
                break;
            }
        }

        if(handler == null){
            System.err.println("Cannot find handler for Tag: "+qName);
        }

        ActHandler act = new ActHandler();
        act.handler = handler;
        act.tag = qName;
        act.attributes = atts;

        if(!handlerStack.isEmpty()){
            act.parent = handlerStack.peek();
        }

        path.push(qName);
        handlerStack.push(act);

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(handlerStack.peek().tag.equals(qName)){
            path.pop();
            ActHandler handler = handlerStack.pop();
            Object o = handler.handler.handle(
                    handler.tag,
                    handler.attributes,
                    handler.childs,
                    context
            );
            if(handler.parent != null){
                Map<String, Object> childs = handler.parent.childs;
                if(!childs.containsKey(qName)){
                    childs.put(qName, o);
                }else{
                    Object out;
                    Object other = childs.remove(qName);
                    if(other instanceof List){
                        ((List)other).add(o);
                        out = other;
                    }else{
                        List list = new ArrayList();
                        list.add(other);
                        list.add(o);
                        out = list;
                    }
                    childs.put(qName, out);
                }
            }else{
                root = o;
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = new String(ch, start, length);
        if(!handlerStack.isEmpty()){
            handlerStack.peek().childs.put("content", s);
        }else{
            root = s;
        }
    }


    private class ActHandler{
        ActHandler parent;
        IXMLEventHandler handler;
        String tag;
        Attributes attributes;
        Map<String, Object> childs = new HashMap<>();
    }
}
