package cl.entel.eai.pipeline.reader;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.HubDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.Hub;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HubDAOReader extends DAOReader<HubDAO, List<Hub>>{

    @Autowired
    private DAOConfiguration<HubDAO> configuration;

    public HubDAOReader(){ }

    @Override
    protected void init() {
        this.setConfiguration(configuration);
    }

    @Override
    public List<Hub> process(Void input) throws PipelineException {
        try {
            return this.configuration.getDao().getHubChuck(configuration.getOffset(), configuration.getChunkSize());
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_READER);
        }
    }
}
