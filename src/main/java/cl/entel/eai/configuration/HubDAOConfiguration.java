package cl.entel.eai.pipeline.configuration;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.HubDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

public class HubDAOConfiguration extends DAOConfiguration<HubDAO> {

    public HubDAOConfiguration(HubDAO hubDAO, int chunkSize) {
        this.setOffset(0);
        this.setChunkSize(chunkSize);
        this.setDao(hubDAO);
    }

    public void init() throws PipelineException {
        try {
            this.setTotalRecords(this.getDao().getRecordCount());
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_READER, e.getMessage());
        }
    }
}
