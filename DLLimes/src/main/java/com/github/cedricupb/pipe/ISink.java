package com.github.cedricupb.pipe;


public interface ISink<T> {

    public void process(T in);

}
