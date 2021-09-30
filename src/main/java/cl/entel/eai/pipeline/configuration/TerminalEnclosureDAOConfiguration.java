package cl.entel.eai.pipeline.configuration;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.TerminalEnclosureDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;

public class TerminalEnclosureDAOConfiguration extends DAOConfiguration<TerminalEnclosureDAO> {

    public TerminalEnclosureDAOConfiguration(TerminalEnclosureDAO terminalEnclosureDAO, int chunkSize) {
        this.setOffset(0);
        this.setChunkSize(chunkSize);
        this.setDao(terminalEnclosureDAO);
    }

    public void init() throws PipelineException {
        try {
            this.setTotalRecords(this.getDao().getRecordCount());
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_READER, e.getMessage());
        }
    }
}
