package cl.entel.eai.reader;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.HubDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.Hub;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import cl.entel.eai.pipeline.reader.DAOReader;

import java.util.List;

public class HubDAOReader extends DAOReader<HubDAO, List<Hub>> {

    public HubDAOReader(DAOConfiguration<HubDAO> configuration){
        this.configuration = configuration;
    }

    @Override
    public List<Hub> process(Void input) throws PipelineException {
        try {
            return this.configuration.getDao().getHubChuck(configuration.getOffset(), configuration.getChunkSize());
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_READER, e.getMessage());
        }
    }
}
