package com.github.cedricupb.io.limes;

import org.aksw.limes.core.io.config.Configuration;

/**
 * Created by Cedric Richter on 11.11.17.
 */
public interface ILIMESRunner {


    public ILIMESJob execute(Configuration conf);

}
