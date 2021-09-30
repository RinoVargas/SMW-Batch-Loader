package cl.entel.eai.pipeline.runner;

import cl.entel.eai.constants.PipelineError;
import cl.entel.eai.dao.TerminalEnclosureDAO;
import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.model.TerminalEnclosure;
import cl.entel.eai.configuration.TerminalEnclosureDAOConfiguration;
import cl.entel.eai.reader.TerminalEnclosureDAOReader;
import cl.entel.eai.transformer.TerminalEnclosureValidatorTransformer;
import cl.entel.eai.pipeline.transformer.Transformer;
import cl.entel.eai.pipeline.writer.DAOWriter;
import cl.entel.eai.writer.TerminalEnclosureDAOWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TerminalEnclosureDAOPipelineRunner extends DAOPipelineRunner<TerminalEnclosureDAO, List<TerminalEnclosure>> {

    @Autowired
    private TerminalEnclosureDAOConfiguration configuration;

    private TerminalEnclosureDAOReader reader;

    @Override
    protected void init() throws PipelineException{
        reader = new TerminalEnclosureDAOReader(configuration);
        this.setReader(reader);
    }

    @Override
    public void executePipeline() throws PipelineException {

        // Transformer
        Transformer<Void, List<TerminalEnclosure>, List<TerminalEnclosure>> transformer = new TerminalEnclosureValidatorTransformer();

        // Writer
        DAOWriter<TerminalEnclosureDAO, List<TerminalEnclosure>> writer = new TerminalEnclosureDAOWriter();
        writer.setConfiguration(reader.getConfiguration());

        // Execute Pipeline
        this.getPipeline(this.getReader())
                .addHandler(transformer)
                .addHandler(writer)
                .execute();
    }

    @Override
    public void onFinish() throws PipelineException {
        try {
            this.getReader().getConfiguration().getDao().closeConnection();
        } catch (IMGISException e) {
            throw new PipelineException(PipelineError.ERROR_PIPELINE_DB_CLOSE_CONNECTION, e.getMessage());
        }
    }
}
