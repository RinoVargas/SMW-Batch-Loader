package cl.entel.eai.configuration.pipeline;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.BuildingDAO;
import cl.entel.eai.exception.DAOException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;

public class BuildingDAOConfiguration extends DAOConfiguration<BuildingDAO> {

    public BuildingDAOConfiguration(BuildingDAO dao, int chunkSize) {
        this.setOffset(0);
        this.setChunkSize(chunkSize);
        this.setDao(dao);
    }

    public void init() throws PipelineException {
        try {
            this.setTotalRecords(this.getDao().getRecordCount());
        } catch (DAOException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_READER, e.getMessage());
        }
    }
}
