package cl.entel.eai.pipeline.reader;

import cl.entel.eai.exception.NoDataToReceiveException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;

public abstract class DAOReader<D, O> extends Reader<DAOConfiguration<D>, O> {

    public DAOReader() { }

    public abstract O process(Void input) throws PipelineException, NoDataToReceiveException;

    @Override
    public void init() throws PipelineException { }
}