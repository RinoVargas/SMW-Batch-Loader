package cl.entel.eai.pipeline.runner;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.BuildingDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.Building;
import cl.entel.eai.configuration.BuildingDAOConfiguration;
import cl.entel.eai.reader.BuildingDAOReader;
import cl.entel.eai.transformer.BuildingValidatorTransformer;
import cl.entel.eai.pipeline.transformer.Transformer;
import cl.entel.eai.pipeline.writer.BuildingDAOWriter;
import cl.entel.eai.pipeline.writer.DAOWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuildingDAOPipelineRunner extends DAOPipelineRunner<BuildingDAO, List<Building>> {

    @Autowired
    private BuildingDAOConfiguration configuration;

    private BuildingDAOReader reader;

    @Override
    protected void init() throws PipelineException{
        reader = new BuildingDAOReader(configuration);
        this.setReader(reader);
    }

    @Override
    public void executePipeline() throws PipelineException {

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
