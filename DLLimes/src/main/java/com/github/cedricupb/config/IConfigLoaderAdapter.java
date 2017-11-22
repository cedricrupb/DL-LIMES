package com.github.cedricupb.config;

import org.apache.commons.configuration2.Configuration;

/**
 * Created by Cedric Richter on 12.11.17.
 */
public interface IConfigLoaderAdapter {

    public boolean isLoading(Class<? extends IObjectConfiguration> dest, Class<? extends Configuration> src);

    public IObjectConfiguration load(Configuration config);

}
