package com.github.cedricupb.refine.sourceext;

import org.aksw.limes.core.io.config.KBInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LazyQueryFactory {

    private Set<ResultDescription> closeable = new HashSet<>();

    public LazyQuery create(KBInfo kb, String query){
        return new LazyQuery(this, kb, query);
    }

    public void close(){
        for(ResultDescription d: closeable)
            d.close();
    }


    void registerDescription(ResultDescription desc){
        closeable.add(desc);
    }

    void unregisterDescription(ResultDescription desc){
        closeable.remove(desc);
    }

}
