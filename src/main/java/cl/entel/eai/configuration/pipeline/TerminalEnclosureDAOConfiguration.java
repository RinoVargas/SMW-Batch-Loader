package cl.entel.eai.configuration.pipeline;

import cl.entel.eai.dao.TerminalEnclosureDAO;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;

public class TerminalEnclosureDAOConfiguration extends DAOConfiguration<TerminalEnclosureDAO> {

    public TerminalEnclosureDAOConfiguration(TerminalEnclosureDAO terminalEnclosureDAO, int chunkSize) {
        this.setOffset(0);
        this.setChunkSize(chunkSize);
        this.setDao(terminalEnclosureDAO);
    }
}
