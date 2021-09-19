package cl.entel.eai.pipeline.transformer;

import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.Handler;

public abstract class Transformer<C, I, O> implements Handler<C, I, O> {

    protected C configuration;

    Transformer() { }

    public C getConfiguration() {
        return configuration;
    }

    public void setConfiguration(C configuration) {
        this.configuration = configuration;
    }

    protected abstract void init();

    public abstract O process(I input) throws PipelineException;


}
