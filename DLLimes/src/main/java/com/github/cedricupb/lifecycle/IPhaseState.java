package com.github.cedricupb.lifecycle;

public interface IPhaseState {

    public Object getProperty(String prop);

    public void setProperty(String prop, Object o);
}
