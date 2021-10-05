package cl.entel.eai.pipeline.runner;

import cl.entel.eai.exception.NoDataToReceiveException;
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

        while (true){
            this.onBeforePipelineExecuting(
                    this.getReader().getConfiguration().getChunkSize(),
                    this.getReader().getConfiguration().getOffset());
            try {
                this.executePipeline();

                this.getReader().getConfiguration().incrementOffset(
                        this.getReader().getConfiguration().getChunkSize());

                this.onAfterPipelineExecuting(
                        this.getReader().getConfiguration().getChunkSize(),
                        this.getReader().getConfiguration().getOffset());
            } catch (NoDataToReceiveException e) {
                break;
            }
        }

        this.onFinishPipelineExecuting(
                this.getReader().getConfiguration().getOffset());
    }

    public void onBeforeInit() throws PipelineException { };

    public void onAfterInit(DAOConfiguration<D> configuration) throws PipelineException { };

    public void onBeforePipelineExecuting(long chunkSize,long offset) throws PipelineException { };

    public void onAfterPipelineExecuting(long chunkSize,long offset) throws PipelineException { };

    public void onFinishPipelineExecuting(long offset) throws PipelineException { };
}
