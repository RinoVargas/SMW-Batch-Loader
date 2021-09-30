package cl.entel.eai.pipeline.writer;

import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;

public abstract class DAOWriter<D, I> extends Writer<DAOConfiguration<D>, I> {

    public DAOWriter() { }

    public abstract Void process(I input) throws PipelineException;
}
