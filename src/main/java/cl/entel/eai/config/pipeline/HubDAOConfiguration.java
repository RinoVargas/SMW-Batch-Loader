package cl.entel.eai.config.pipeline;

import cl.entel.eai.dao.HubDAO;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

public class HubDAOConfiguration extends DAOConfiguration<HubDAO> {

    @Autowired
    private HubDAO dao;

    public HubDAOConfiguration(int chunkSize) {
        this.setOffset(0);
        this.setChunkSize(chunkSize);
        this.setDao(this.dao);
    }
}
