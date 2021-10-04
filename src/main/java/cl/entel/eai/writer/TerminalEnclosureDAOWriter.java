package cl.entel.eai.writer;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.TerminalEnclosureDAO;
import cl.entel.eai.exception.DAOException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.TerminalEnclosure;
import cl.entel.eai.pipeline.writer.DAOWriter;

import java.util.List;

public class TerminalEnclosureDAOWriter extends DAOWriter<TerminalEnclosureDAO, List<TerminalEnclosure>> {

    public TerminalEnclosureDAOWriter() { }

    @Override
    protected void init() { }

    @Override
    public Void process(List<TerminalEnclosure> input) throws PipelineException {
        try {
            this.getConfiguration().getDao().createGeoTerminalEnclosure(input);
        } catch (DAOException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_WRITER, e.getMessage());
        }

        return null;
    }
}
