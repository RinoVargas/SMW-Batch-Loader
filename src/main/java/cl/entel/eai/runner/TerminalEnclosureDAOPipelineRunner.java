package cl.entel.eai.runner;

import ch.qos.logback.classic.Logger;
import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.TerminalEnclosureDAO;
import cl.entel.eai.exception.DAOException;
import cl.entel.eai.exception.NoDataToReceiveException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.TerminalEnclosure;
import cl.entel.eai.configuration.pipeline.TerminalEnclosureDAOConfiguration;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import cl.entel.eai.pipeline.runner.DAOPipelineRunner;
import cl.entel.eai.reader.TerminalEnclosureDAOReader;
import cl.entel.eai.transformer.TerminalEnclosureValidatorTransformer;
import cl.entel.eai.pipeline.transformer.Transformer;
import cl.entel.eai.pipeline.writer.DAOWriter;
import cl.entel.eai.writer.TerminalEnclosureDAOWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TerminalEnclosureDAOPipelineRunner extends DAOPipelineRunner<TerminalEnclosureDAO, List<TerminalEnclosure>> {

    @Autowired
    private TerminalEnclosureDAOConfiguration configuration;

    private TerminalEnclosureDAOReader reader;

    @Autowired
    private Logger logger;

    @Override
    protected void init() throws PipelineException{
        reader = new TerminalEnclosureDAOReader(configuration);
        this.setReader(reader);
    }

    @Value("${batch.loader.terminalEnclosure.erase-geometry-table}")
    private boolean cleanGeometryTable;

    @Override
    public void executePipeline() throws PipelineException, NoDataToReceiveException {

        // Transformer
        Transformer<Void, List<TerminalEnclosure>, List<TerminalEnclosure>> transformer = new TerminalEnclosureValidatorTransformer();

        // Writer
        DAOWriter<TerminalEnclosureDAO, List<TerminalEnclosure>> writer = new TerminalEnclosureDAOWriter();
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
            throw new PipelineException(PipelineError.ERROR_PIPELINE_DB_CLOSE_CONNECTION, e.getMessage());
        }
    }

    @Override
    public void onBeforeInit() throws PipelineException {
        logger.info("Iniciando carga de Terminal Enclosure's...");
    }

    @Override
    public void onAfterInit(DAOConfiguration<TerminalEnclosureDAO> configuration) throws PipelineException {
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
        logger.info("Finalizando carga de Terminal Enclosure's...");
        this.closeConnection();
        logger.info("REGISTROS CARGADOS CORRECTAMENTE");
        logger.info(String.format("TOTAL DE REGISTROS CARGADOS: %d", offset));
    }
}
