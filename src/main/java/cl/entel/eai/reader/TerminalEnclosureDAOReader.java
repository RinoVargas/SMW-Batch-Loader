package cl.entel.eai.pipeline.reader;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.TerminalEnclosureDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.TerminalEnclosure;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;

import java.util.List;

public class TerminalEnclosureDAOReader extends DAOReader<TerminalEnclosureDAO, List<TerminalEnclosure>>{

    public TerminalEnclosureDAOReader(DAOConfiguration<TerminalEnclosureDAO> configuration){
        this.configuration = configuration;
    }

    @Override
    public List<TerminalEnclosure> process(Void input) throws PipelineException {
        try {
            return this.configuration.getDao().getTerminalEnclosureChuck(configuration.getOffset(), configuration.getChunkSize());
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_READER, e.getMessage());
        }
    }
}
