package cl.entel.eai.runner;

import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;

public abstract class DAOPipelineRunner<D, O> extends PipelineRunner<DAOConfiguration<D>, O>{

    DAOPipelineRunner() { }

    @Override
    public void run() throws PipelineException{
        long offset = this.getReader().getConfiguration().getOffset();
        long chunkSize = this.getReader().getConfiguration().getChunkSize();
        long total = this.getReader().getConfiguration().getTotalRecords();

        do {
            this.executePipeline();
            offset += chunkSize;
        } while (offset < total);
    }

    public abstract void executePipeline() throws PipelineException;
}
