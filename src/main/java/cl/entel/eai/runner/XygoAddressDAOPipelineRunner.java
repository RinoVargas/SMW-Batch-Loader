package cl.entel.eai.runner;

import ch.qos.logback.classic.Logger;
import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.XygoAddressDAO;
import cl.entel.eai.exception.DAOException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.XygoAddress;
import cl.entel.eai.configuration.pipeline.XygoAddressDAOConfiguration;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import cl.entel.eai.pipeline.runner.DAOPipelineRunner;
import cl.entel.eai.reader.XygoAddressDAOReader;
import cl.entel.eai.pipeline.transformer.Transformer;
import cl.entel.eai.transformer.XygoAdressValidatorTransformer;
import cl.entel.eai.pipeline.writer.DAOWriter;
import cl.entel.eai.writer.XygoAddressDAOWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class XygoAddressDAOPipelineRunner extends DAOPipelineRunner<XygoAddressDAO, List<XygoAddress>> {

    @Autowired
    private XygoAddressDAOConfiguration configuration;

    private XygoAddressDAOReader reader;

    @Autowired
    private Logger logger;

    @Override
    protected void init() throws PipelineException{
        reader = new XygoAddressDAOReader(configuration);
        this.setReader(reader);
    }

    @Value("${batch.loader.xygoAddress.erase-geometry-table}")
    private boolean cleanGeometryTable;

    @Override
    public void executePipeline() throws PipelineException {

        // Transformer
        Transformer<Void, List<XygoAddress>, List<XygoAddress>> transformer = new XygoAdressValidatorTransformer();

        // Writer
        DAOWriter<XygoAddressDAO, List<XygoAddress>> writer = new XygoAddressDAOWriter();
        writer.setConfiguration(reader.getConfiguration());

        // Execute Pipeline
        this.getPipeline(this.getReader())
                .addHandler(transformer)
                .addHandler(writer)
                .execute();
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
        logger.info("Iniciando carga de Direcciones...");
    }

    @Override
    public void onAfterInit(DAOConfiguration<XygoAddressDAO> configuration) throws PipelineException {
        logger.info(String.format("Cantidad de registros ha ser cargados: %d", configuration.getTotalRecords()));

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
    public void onBeforePipelineExecuting(long chunkSize, long offset, long total) throws PipelineException {
        logger.info(String.format("Procesando %d registros...", chunkSize));
    }

    @Override
    public void onAfterPipelineExecuting(long chunkSize, long offset, long total) throws PipelineException {
        logger.info(String.format("Lote de registros cargados: %d / %d", offset, total));
    }

    @Override
    public void onFinishPipelineExecuting(long offset, long total) throws PipelineException {
        logger.info("Finalizando carga de Direcciones...");
        this.closeConnection();
        logger.info(String.format("REGISTROS CARGADOS CORRECTAMENTE: %d", total));
    }
}
