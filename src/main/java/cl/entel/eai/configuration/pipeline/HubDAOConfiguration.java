package cl.entel.eai.configuration.pipeline;

import cl.entel.eai.dao.HubDAO;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;

public class HubDAOConfiguration extends DAOConfiguration<HubDAO> {

    public HubDAOConfiguration(HubDAO hubDAO, int chunkSize) {
        this.setOffset(0);
        this.setChunkSize(chunkSize);
        this.setDao(hubDAO);
    }

}
