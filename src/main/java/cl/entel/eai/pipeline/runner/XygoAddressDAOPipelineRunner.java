package cl.entel.eai.pipeline.runner;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.XygoAddressDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.XygoAddress;
import cl.entel.eai.configuration.XygoAddressDAOConfiguration;
import cl.entel.eai.reader.XygoAddressDAOReader;
import cl.entel.eai.pipeline.transformer.Transformer;
import cl.entel.eai.pipeline.transformer.XygoAdressValidatorTransformer;
import cl.entel.eai.pipeline.writer.DAOWriter;
import cl.entel.eai.pipeline.writer.XygoAddressDAOWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class XygoAddressDAOPipelineRunner extends DAOPipelineRunner<XygoAddressDAO, List<XygoAddress>> {

    @Autowired
    private XygoAddressDAOConfiguration configuration;

    private XygoAddressDAOReader reader;

    @Override
    protected void init() throws PipelineException{
        reader = new XygoAddressDAOReader(configuration);
        this.setReader(reader);
    }

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

    @Override
    public void onFinish() throws PipelineException {
        try {
            this.getReader().getConfiguration().getDao().closeConnection();
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_DB_CLOSE_CONNECTION, e.getMessage());
        }
    }
}
