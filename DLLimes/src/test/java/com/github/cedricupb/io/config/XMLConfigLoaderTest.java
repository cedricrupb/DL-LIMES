package com.github.cedricupb.io.config;

import org.junit.Test;

import static org.junit.Assert.*;

public class XMLConfigLoaderTest {
    @Test
    public void load() throws Exception {

        XMLConfigLoader loader = new XMLConfigLoader();

        XMLRunningConfiguration config = loader.load("/Users/cedricrichter/Documents/DL-LIMES/DLLimes/src/main/resources/config/test-config.xml");

        System.out.println(config);
    }

}