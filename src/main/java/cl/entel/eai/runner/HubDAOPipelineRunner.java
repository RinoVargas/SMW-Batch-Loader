package cl.entel.eai.runner;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.pipeline.configuration.HubDAOConfiguration;
import cl.entel.eai.dao.HubDAO;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.Hub;
import cl.entel.eai.pipeline.reader.HubDAOReader;
import cl.entel.eai.pipeline.transformer.HubValidatorTransformer;
import cl.entel.eai.pipeline.transformer.Transformer;
import cl.entel.eai.pipeline.writer.DAOWriter;
import cl.entel.eai.pipeline.writer.HubDAOWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HubDAOPipelineRunner extends DAOPipelineRunner<HubDAO, List<Hub>> {

    @Autowired
    private HubDAOConfiguration hubDAOConfiguration;

    private HubDAOReader hubDAOReader;

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

    @Override
    public void onFinish() throws PipelineException {
        try {
            this.getReader().getConfiguration().getDao().closeConnection();
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_DB_CLOSE_CONNECTION, e.getMessage());
        }
    }
}
