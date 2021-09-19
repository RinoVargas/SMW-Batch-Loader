package cl.entel.eai.pipeline.reader;

import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.Handler;

public abstract class Reader<C, O> implements Handler<C, Void, O> {

    protected C configuration;

    Reader() { }

    protected abstract void init();

    public C getConfiguration() {
        return configuration;
    }

    public void setConfiguration(C configuration) {
        this.configuration = configuration;
    }

    public abstract O process(Void input) throws PipelineException;
}
