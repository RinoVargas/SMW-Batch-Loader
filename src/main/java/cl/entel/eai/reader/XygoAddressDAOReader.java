package cl.entel.eai.reader;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.XygoAddressDAO;
import cl.entel.eai.exception.DAOException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.XygoAddress;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import cl.entel.eai.pipeline.reader.DAOReader;

import java.util.List;

public class XygoAddressDAOReader extends DAOReader<XygoAddressDAO, List<XygoAddress>> {

    public XygoAddressDAOReader(DAOConfiguration<XygoAddressDAO> configuration){
        this.configuration = configuration;
    }

    @Override
    public List<XygoAddress> process(Void input) throws PipelineException {
        try {
            return this.configuration.getDao().getXygoAddressChuck(configuration.getOffset(), configuration.getChunkSize());
        } catch (DAOException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_READER, e.getMessage());
        }
    }
}
