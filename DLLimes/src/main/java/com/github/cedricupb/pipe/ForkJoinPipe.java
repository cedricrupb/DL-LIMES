package com.github.cedricupb.pipe;

import java.util.ArrayList;
import java.util.List;

public class ForkJoinPipe<S,T> implements IPipe<List<? extends S>, List<? extends T>> {

    private ISink<List<? extends T>> sink;
    private IPipe<S,T> processor;

    public ForkJoinPipe(IPipe<S, T> processor) {
        this.processor = processor;
    }


    @Override
    public void setSink(ISink<List<? extends T>> sink) {
        this.sink = sink;
    }

    @Override
    public void process(List<? extends S> in) {
        if(sink == null) return;

        ListSink buffer = new ListSink();
        processor.setSink(buffer);

        for(S s: in){
            processor.process(s);
        }

        sink.process(buffer.getList());
    }

    private class ListSink implements ISink<T>{

        private List<T> out = new ArrayList<>();

        @Override
        public void process(T in) {
            out.add(in);
        }

        public List<T> getList(){
            return out;
        }
    }
}
