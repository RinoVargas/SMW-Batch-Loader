package cl.entel.eai.configuration;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.XygoAddressDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;

public class XygoAddressDAOConfiguration extends DAOConfiguration<XygoAddressDAO> {

    public XygoAddressDAOConfiguration(XygoAddressDAO dao, int chunkSize) {
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
