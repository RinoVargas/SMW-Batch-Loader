package cl.entel.eai.runner;

import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class DAOPipelineRunner<D, O> extends PipelineRunner<DAOConfiguration<D>, O>{

    private static final Log logger = LogFactory.getLog(DAOPipelineRunner.class);

    DAOPipelineRunner() { }

    @Override
    public void run() throws PipelineException{

        logger.info("Inicializando carga de registros...");
        this.init();

        long offset = this.getReader().getConfiguration().getOffset();
        long total = this.getReader().getConfiguration().getTotalRecords();

        while (offset < total){
            this.getReader().getConfiguration().computeChuckSize();
            long chunkSize = this.getReader().getConfiguration().getChunkSize();


            this.executePipeline();

            offset += chunkSize;
            this.getReader().getConfiguration().incrementOffset(chunkSize);

            logger.info(String.format("Lote de registros cargados: %d / %d", offset, total));
        }

        logger.info("Finalizando carga de registros...");
        this.onFinish();
        logger.info(String.format("Total de Registros cargados: %d", total));
    }

    private long computeChuckSize(long offset, long chunkSize, long total) {
        return (offset + chunkSize) >= total ? total - offset : chunkSize;
    }

    public abstract void executePipeline() throws PipelineException;
}
