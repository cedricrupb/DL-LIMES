package com.github.cedricupb.io.config;

import java.util.Map;

public interface IConfigParser {

    public boolean isParsing(String key, Class<?> clazz);

    public Map<String, Class> getDependencies();

}
