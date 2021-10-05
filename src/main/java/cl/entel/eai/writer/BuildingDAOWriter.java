package cl.entel.eai.writer;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.BuildingDAO;
import cl.entel.eai.exception.DAOException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.Building;
import cl.entel.eai.pipeline.writer.DAOWriter;

import java.util.List;

public class BuildingDAOWriter extends DAOWriter<BuildingDAO, List<Building>> {

    public BuildingDAOWriter() { }

    @Override
    protected void init() { }

    @Override
    public Void process(List<Building> input) throws PipelineException {
        try {
            this.setOutputRecords(input.size());
            this.getConfiguration().getDao().createGeoBuilding(input);
        } catch (DAOException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_WRITER, e.getMessage());
        }

        return null;
    }
}
