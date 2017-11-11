package com.github.cedricupb.io.limes;

import org.aksw.limes.core.io.mapping.AMapping;

import java.util.concurrent.TimeUnit;

/**
 * Created by Cedric Richter on 11.11.17.
 */
public interface ILIMESJob {

    public AMapping queryResult();

    public AMapping queryResult(long timeout, TimeUnit unit);

}
