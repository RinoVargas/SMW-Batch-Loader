package cl.entel.eai.pipeline.writer;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.BuildingDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.Building;

import java.util.List;

public class BuildingDAOWriter extends DAOWriter<BuildingDAO, List<Building>> {

    public BuildingDAOWriter() { }

    @Override
    protected void init() { }

    @Override
    public Void process(List<Building> input) throws PipelineException {
        try {
            this.getConfiguration().getDao().createGeoBuilding(input);
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_WRITER, e.getMessage());
        }

        return null;
    }
}
