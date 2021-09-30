package cl.entel.eai.pipeline.configuration;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.BuildingDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;

public class BuildingDAOConfiguration extends DAOConfiguration<BuildingDAO> {

    public BuildingDAOConfiguration(BuildingDAO dao, int chunkSize) {
        this.setOffset(0);
        this.setChunkSize(chunkSize);
        this.setDao(dao);
    }

    public void init() throws PipelineException {
        try {
            this.setTotalRecords(this.getDao().getRecordCount());
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_READER, e.getMessage());
        }
    }
}
