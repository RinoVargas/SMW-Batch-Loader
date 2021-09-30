package cl.entel.eai.reader;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.BuildingDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.Building;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import cl.entel.eai.pipeline.reader.DAOReader;

import java.util.List;

public class BuildingDAOReader extends DAOReader<BuildingDAO, List<Building>> {

    public BuildingDAOReader(DAOConfiguration<BuildingDAO> configuration){
        this.configuration = configuration;
    }

    @Override
    public List<Building> process(Void input) throws PipelineException {
        try {
            return this.configuration.getDao().getBuildingChuck(configuration.getOffset(), configuration.getChunkSize());
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_READER, e.getMessage());
        }
    }
}
