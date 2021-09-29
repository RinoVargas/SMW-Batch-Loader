package cl.entel.eai.pipeline.configuration;

import cl.entel.eai.exception.PipelineException;

public interface Configuration {

    void init() throws PipelineException;
}
