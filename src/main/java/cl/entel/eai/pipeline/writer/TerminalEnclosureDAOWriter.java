package cl.entel.eai.pipeline.writer;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.TerminalEnclosureDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.TerminalEnclosure;

import java.util.List;

public class TerminalEnclosureDAOWriter extends DAOWriter<TerminalEnclosureDAO, List<TerminalEnclosure>> {

    public TerminalEnclosureDAOWriter() { }

    @Override
    protected void init() { }

    @Override
    public Void process(List<TerminalEnclosure> input) throws PipelineException {
        try {
            this.getConfiguration().getDao().createGeoTerminalEnclosure(input);
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_WRITER, e.getMessage());
        }

        return null;
    }
}
