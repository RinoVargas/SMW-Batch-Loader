package cl.entel.eai.pipeline.runner;

import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.Pipeline;
import cl.entel.eai.pipeline.reader.Reader;

public abstract class PipelineRunner<C, O> {

    private Reader<C, O> reader;

    public PipelineRunner() { }

    protected void init() throws PipelineException{  }

    public abstract void run() throws PipelineException;

    public Pipeline<C, Void, O> getPipeline(Reader<C, O> reader) {
        return new Pipeline<>(reader);
    }

    public Reader<C, O> getReader() {
        return reader;
    }

    public void setReader(Reader<C, O> reader) {
        this.reader = reader;
    }
}
