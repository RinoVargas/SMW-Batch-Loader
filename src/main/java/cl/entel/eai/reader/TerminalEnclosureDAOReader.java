package cl.entel.eai.reader;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.TerminalEnclosureDAO;
import cl.entel.eai.exception.DAOException;
import cl.entel.eai.exception.NoDataToReceiveException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.TerminalEnclosure;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import cl.entel.eai.pipeline.reader.DAOReader;

import java.util.List;

public class TerminalEnclosureDAOReader extends DAOReader<TerminalEnclosureDAO, List<TerminalEnclosure>> {

    public TerminalEnclosureDAOReader(DAOConfiguration<TerminalEnclosureDAO> configuration){
        this.configuration = configuration;
    }

    @Override
    public List<TerminalEnclosure> process(Void input) throws PipelineException, NoDataToReceiveException {
        try {
            List<TerminalEnclosure> terminalEnclosures = this.configuration.getDao().getTerminalEnclosureChuck(configuration.getOffset(), configuration.getChunkSize());
            if (terminalEnclosures.isEmpty()) {
                throw new NoDataToReceiveException();
            }

            return terminalEnclosures;
        } catch (DAOException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_READER, e.getMessage());
        }
    }
}
