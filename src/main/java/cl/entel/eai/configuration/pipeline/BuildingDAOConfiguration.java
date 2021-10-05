package cl.entel.eai.configuration.pipeline;

import cl.entel.eai.dao.BuildingDAO;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;

public class BuildingDAOConfiguration extends DAOConfiguration<BuildingDAO> {

    public BuildingDAOConfiguration(BuildingDAO dao, int chunkSize) {
        this.setOffset(0);
        this.setChunkSize(chunkSize);
        this.setDao(dao);
    }
}
