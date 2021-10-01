package cl.entel.eai.runner;

import ch.qos.logback.classic.Logger;
import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.configuration.pipeline.HubDAOConfiguration;
import cl.entel.eai.dao.HubDAO;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.Hub;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import cl.entel.eai.pipeline.runner.DAOPipelineRunner;
import cl.entel.eai.reader.HubDAOReader;
import cl.entel.eai.transformer.HubValidatorTransformer;
import cl.entel.eai.pipeline.transformer.Transformer;
import cl.entel.eai.pipeline.writer.DAOWriter;
import cl.entel.eai.writer.HubDAOWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HubDAOPipelineRunner extends DAOPipelineRunner<HubDAO, List<Hub>> {

    @Autowired
    private HubDAOConfiguration hubDAOConfiguration;

    private HubDAOReader hubDAOReader;

    @Autowired
    private Logger logger;

    @Override
    protected void init() throws PipelineException{
        hubDAOReader = new HubDAOReader(hubDAOConfiguration);
        this.setReader(hubDAOReader);
    }

    @Override
    public void executePipeline() throws PipelineException {

        // Transformer
        Transformer<Void, List<Hub>, List<Hub>> transformer = new HubValidatorTransformer();

        // Writer
        DAOWriter<HubDAO, List<Hub>> writer = new HubDAOWriter();
        writer.setConfiguration(hubDAOReader.getConfiguration());

        // Execute Pipeline
        this.getPipeline(this.getReader())
                .addHandler(transformer)
                .addHandler(writer)
                .execute();
    }

    public void closeConnection() throws PipelineException {
        try {
            logger.info("Cerrando conexiones a bases de datos...");
            this.getReader().getConfiguration().getDao().closeConnection();
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_DB_CLOSE_CONNECTION, e.getMessage());
        }
    }

    @Override
    public void onBeforeInit() throws PipelineException {
        logger.info("Iniciando carga de Hubs...");
    }

    @Override
    public void onAfterInit(DAOConfiguration<HubDAO> configuration) throws PipelineException {
        logger.info(String.format("Cantidad de registros ha ser cargados: %d", configuration.getTotalRecords()));
    }

    @Override
    public void onBeforePipelineExecuting(long chunkSize, long offset, long total) throws PipelineException {
        logger.info(String.format("Procesando %d registros...", chunkSize));
    }

    @Override
    public void onAfterPipelineExecuting(long chunkSize, long offset, long total) throws PipelineException {
        logger.info(String.format("Lote de registros cargados: %d / %d", offset, total));
    }

    @Override
    public void onFinishPipelineExecuting(long offset, long total) throws PipelineException {
        logger.info("Finalizando carga de Hubs...");
        this.closeConnection();
        logger.info(String.format("REGISTROS CARGADOS CORRECTAMENTE: %d", total));
    }
}
