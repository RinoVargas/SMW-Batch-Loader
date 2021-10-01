package cl.entel.eai.pipeline.runner;

import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class DAOPipelineRunner<D, O> extends PipelineRunner<DAOConfiguration<D>, O>{

    private static final Log logger = LogFactory.getLog(DAOPipelineRunner.class);

    public DAOPipelineRunner() { }

    @Override
    public void run() throws PipelineException{

        this.onBeforeInit();
        this.init();
        this.onAfterInit(this.getReader().getConfiguration());

        long offset = this.getReader().getConfiguration().getOffset();
        long total = this.getReader().getConfiguration().getTotalRecords();

        while (offset < total){
            this.getReader().getConfiguration().computeChuckSize();
            long chunkSize = this.getReader().getConfiguration().getChunkSize();

            this.onBeforePipelineExecuting(chunkSize, offset, total);
            this.executePipeline();

            offset += chunkSize;
            this.getReader().getConfiguration().incrementOffset(chunkSize);

            this.onAfterPipelineExecuting(chunkSize, offset, total);
        }

        this.onFinishPipelineExecuting(offset, total);
    }

    private long computeChuckSize(long offset, long chunkSize, long total) {
        return (offset + chunkSize) >= total ? total - offset : chunkSize;
    }

    public abstract void executePipeline() throws PipelineException;

    public void onBeforeInit() throws PipelineException { };

    public void onAfterInit(DAOConfiguration<D> configuration) throws PipelineException { };

    public void onBeforePipelineExecuting(long chunkSize,long offset, long total) throws PipelineException { };

    public void onAfterPipelineExecuting(long chunkSize,long offset, long total) throws PipelineException { };

    public void onFinishPipelineExecuting(long offset, long total) throws PipelineException { };
}
