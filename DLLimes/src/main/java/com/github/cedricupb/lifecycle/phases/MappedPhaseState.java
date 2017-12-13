package com.github.cedricupb.lifecycle.phases;

import com.github.cedricupb.lifecycle.IPhaseState;

import java.util.HashMap;
import java.util.Map;

public class MappedPhaseState implements IPhaseState {

    private Map<String, Object> properties = new HashMap<>();

    @Override
    public Object getProperty(String prop) {
        return properties.get(prop);
    }

    @Override
    public void setProperty(String prop, Object o) {
        properties.put(prop, o);
    }
}
