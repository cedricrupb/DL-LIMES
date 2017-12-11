package com.github.cedricupb.pipe;


public class PipeReturn<S, T> {

    private BufferSink sink;
    private ISink<S> in;

    public PipeReturn(ISink<S> inSink, IPipe<?, T> outPipe) {
        this.in = inSink;
        this.sink = new BufferSink();
        outPipe.setSink(sink);
    }

    public T process(S obj) {
        in.process(obj);
        return sink.get();
    }


    private class BufferSink implements ISink<T> {
        private T buffer;

        public T get() {
            return buffer;
        }

        public void process(T obj) {
            this.buffer = obj;
        }

    }

}
