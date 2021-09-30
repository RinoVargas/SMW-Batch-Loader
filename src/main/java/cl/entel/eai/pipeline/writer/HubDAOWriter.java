package cl.entel.eai.pipeline.writer;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.HubDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.Hub;

import java.util.List;

public class HubDAOWriter extends DAOWriter<HubDAO, List<Hub>> {

    public HubDAOWriter() { }

    @Override
    protected void init() { }

    @Override
    public Void process(List<Hub> input) throws PipelineException {
        try {
            configuration.getDao().createGeoHubs(input);
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_WRITER, e.getMessage());
        }

        return null;
    }
}
