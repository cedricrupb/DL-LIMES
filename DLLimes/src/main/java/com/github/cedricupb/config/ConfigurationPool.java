package com.github.cedricupb.config;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.lucene.search.QueryCachingPolicy;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cedric Richter on 12.11.17.
 */
public class ConfigurationPool {

    private static ConfigurationPool defaultInstance;

    public ConfigurationPool getDefaultInstance(){
        if (defaultInstance == null){
            defaultInstance = new ConfigurationPool();
        }
        return defaultInstance;
    }


    private Map<ConfigKey, IObjectConfiguration> configs;
    private List<IConfigLoaderAdapter> adapters;


    protected ConfigurationPool(){
        configs = new HashMap<>();
        adapters = new ArrayList<>();
    }


    private IObjectConfiguration getConfig(ConfigKey key){
        return configs.get(key);
    }

    public <T extends IObjectConfiguration> T getConfig(String key,Class<? extends T> clazz){
        try{
            return (T) getConfig(new ConfigKey(key,clazz));
        }catch(ClassCastException e){
            return null;
        }
    }

    public <T extends IObjectConfiguration> T getConfig(Class<? extends T> clazz){
        return getConfig(null,clazz);
    }

    private Configuration loadCfg(String path, String format){
        Configurations configs = new Configurations();
        try{
            if(format.equalsIgnoreCase("XML")){
                return configs.xml(path);
            }
            //TODO add more src types
        } catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
        }
        return null;
    }

    private Configuration loadCfg(String path){
        if(path.trim().endsWith("xml")){
            return loadCfg(path, "XML");
        }
        return null;
    }

    private IObjectConfiguration load(String key, Configuration cfg, IConfigLoaderAdapter adapter){
        if(cfg == null)return null;
        IObjectConfiguration o = adapter.load(cfg);

        ConfigKey lKey = new ConfigKey(key,o.getClass());
        configs.put(lKey,o);

        return getConfig(lKey);
    }

    private <T extends IObjectConfiguration> T load(String key, Configuration cfg, Class<T> clazz){
        if(cfg == null)return null;
        for(IConfigLoaderAdapter adapter: adapters){
            if(adapter.isLoading(clazz,cfg.getClass())){
                try {
                    return (T) load(key, cfg, adapter);
                }catch(ClassCastException e){}
            }
        }
        return null;
    }

    public <T extends IObjectConfiguration> T load(String key, Class<T> clazz, String path, String format){
        return load(key, loadCfg(path, format), clazz);
    }

    public <T extends IObjectConfiguration> T load(String key, Class<T> clazz, String path){
        return load(key, loadCfg(path), clazz);
    }

    public <T extends IObjectConfiguration> T load(Class<T> clazz, String path){
        return load(null, loadCfg(path), clazz);
    }



    private class ConfigKey{

        String key;
        Class<?> clazz;

        public ConfigKey(String key, Class<?> clazz){
            this.key = key;
            this.clazz = clazz;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ConfigKey)) return false;

            ConfigKey configKey = (ConfigKey) o;

            if (key != null ? !key.equals(configKey.key) : configKey.key != null) return false;
            return clazz.equals(configKey.clazz);
        }

        @Override
        public int hashCode() {
            int result = key != null ? key.hashCode() : 0;
            result = 31 * result + clazz.hashCode();
            return result;
        }
    }

}
