package cl.entel.eai.runner;

import ch.qos.logback.classic.Logger;
import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.BuildingDAO;
import cl.entel.eai.exception.DAOException;
import cl.entel.eai.exception.NoDataToReceiveException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.Building;
import cl.entel.eai.configuration.pipeline.BuildingDAOConfiguration;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import cl.entel.eai.pipeline.runner.DAOPipelineRunner;
import cl.entel.eai.reader.BuildingDAOReader;
import cl.entel.eai.transformer.BuildingValidatorTransformer;
import cl.entel.eai.pipeline.transformer.Transformer;
import cl.entel.eai.util.LoggerUtil;
import cl.entel.eai.writer.BuildingDAOWriter;
import cl.entel.eai.pipeline.writer.DAOWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class BuildingDAOPipelineRunner extends DAOPipelineRunner<BuildingDAO, List<Building>> {

    @Autowired
    private BuildingDAOConfiguration configuration;

    private BuildingDAOReader reader;

    @Autowired
    private Logger logger;

    @Override
    protected void init() throws PipelineException{
        reader = new BuildingDAOReader(configuration);
        this.setReader(reader);
    }

    @Value("${batch.loader.building.erase-geometry-table}")
    private boolean cleanGeometryTable;

    @Override
    public void executePipeline() throws PipelineException, NoDataToReceiveException {

        // Transformer
        Transformer<Void, List<Building>, List<Building>> transformer = new BuildingValidatorTransformer();

        // Writer
        DAOWriter<BuildingDAO, List<Building>> writer = new BuildingDAOWriter();
        writer.setConfiguration(reader.getConfiguration());

        // Execute Pipeline
        this.getPipeline(this.getReader())
                .addHandler(transformer)
                .addHandler(writer)
                .execute();

        this.getReader().getConfiguration().setChunkSize(writer.getOutputRecords());
    }

    public void closeConnection() throws PipelineException {
        try {
            logger.info("Cerrando conexiones...");
            this.getReader().getConfiguration().getDao().closeConnection();
        } catch (DAOException e) {
            logger.error(LoggerUtil.formatErrorMessage(PipelineError.ERROR_PIPELINE_DB_CLOSE_CONNECTION, e.getMessage()));
            throw new PipelineException(PipelineError.ERROR_PIPELINE_DB_CLOSE_CONNECTION, e.getMessage());
        }
    }

    @Override
    public void onBeforeInit() throws PipelineException {
        logger.info("Iniciando carga de Buildings...");
    }

    @Override
    public void onAfterInit(DAOConfiguration<BuildingDAO> configuration) throws PipelineException {
        if (this.cleanGeometryTable) {
            try {
                logger.info("Eliminando registros de la tabla geométrica antes de continuar...");
                configuration.getDao().cleanGeometryTable();
                logger.info("Eliminación completada...");
            } catch (DAOException e) {
                throw new PipelineException(PipelineError.ERROR_PIPELINE_ON_AFTER_INIT, e.getMessage());
            }
        }
    }

    @Override
    public void onBeforePipelineExecuting(long chunkSize, long offset) throws PipelineException {
        logger.info("Procesando registros...");
    }

    @Override
    public void onAfterPipelineExecuting(long chunkSize, long offset) throws PipelineException {
        logger.info(String.format("Lote de registros cargados: %d ", offset));
    }

    @Override
    public void onFinishPipelineExecuting(long offset) throws PipelineException {
        logger.info("Finalizando carga de Buildings...");
        this.closeConnection();
        logger.info("REGISTROS CARGADOS CORRECTAMENTE");
        logger.info(String.format("TOTAL DE REGISTROS CARGADOS: %d", offset));
    }
}
