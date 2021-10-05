package cl.entel.eai.configuration.pipeline;

import cl.entel.eai.dao.XygoAddressDAO;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;

public class XygoAddressDAOConfiguration extends DAOConfiguration<XygoAddressDAO> {

    public XygoAddressDAOConfiguration(XygoAddressDAO dao, int chunkSize) {
        this.setOffset(0);
        this.setChunkSize(chunkSize);
        this.setDao(dao);
    }
}
