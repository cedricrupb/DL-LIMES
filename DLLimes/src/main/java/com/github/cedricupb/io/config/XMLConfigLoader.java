package com.github.cedricupb.io.config;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class XMLConfigLoader {

    private XMLHandler handler;

    private void init(){
        if(handler == null){
            handler = new XMLHandler();
            handler.getHandlers().add(new RunningHandler());
            handler.getHandlers().add(new RefineHandler());
            handler.getHandlers().add(new PosNegHandler());
            handler.getHandlers().add(new ExampleHandler());
            handler.getHandlers().add(new KBInfoHandler());
            handler.getHandlers().add(new DLHandler());
            handler.getHandlers().add(new LIMESHandler());
            handler.getHandlers().add(new PrefixHandler());
            handler.getHandlers().add(new DefaultXMLHandler());
        }
    }

    public XMLRunningConfiguration load(String file) {
        init();
        try {
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();


            FileReader reader = new FileReader(file);
            InputSource inputSource = new InputSource(reader);

            xmlReader.setContentHandler(handler);

            xmlReader.parse(inputSource);

            return (XMLRunningConfiguration) handler.getRootAndReset();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }


}
