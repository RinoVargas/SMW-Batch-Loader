package cl.entel.eai.pipeline.writer;

import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.Handler;

public abstract class Writer<C, I> implements Handler<C, I, Void> {

    private C configuration;

    public Writer() { }

    public C getConfiguration() {
        return configuration;
    }

    public void setConfiguration(C configuration) {
        this.configuration = configuration;
    }

    protected abstract void init();

    public abstract Void process(I input) throws PipelineException;

}
