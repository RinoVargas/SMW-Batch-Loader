package cl.entel.eai.pipeline;

import cl.entel.eai.exception.PipelineException;

public interface Handler<C, I, O> {

    O process(I input) throws PipelineException;

}
