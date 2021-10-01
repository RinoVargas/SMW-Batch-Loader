package cl.entel.eai.configuration;

import ch.qos.logback.classic.Logger;
import cl.entel.eai.dao.BuildingDAO;
import cl.entel.eai.dao.TerminalEnclosureDAO;
import cl.entel.eai.dao.XygoAddressDAO;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.configuration.pipeline.BuildingDAOConfiguration;
import cl.entel.eai.configuration.pipeline.HubDAOConfiguration;
import cl.entel.eai.dao.HubDAO;
import cl.entel.eai.configuration.pipeline.TerminalEnclosureDAOConfiguration;
import cl.entel.eai.configuration.pipeline.XygoAddressDAOConfiguration;
import cl.entel.eai.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    // Hub Configuration
    @Bean
    public HubDAO hubDAO(){
        return new HubDAO();
    }

    @Bean
    public HubDAOConfiguration hubDAOConfiguration(HubDAO hubDAO, @Value("${batch.loader.hub.chunksize}") int chunkSize) throws PipelineException {
        HubDAOConfiguration hubDAODAOConfiguration = new HubDAOConfiguration(hubDAO, chunkSize);
        hubDAODAOConfiguration.init();

        return hubDAODAOConfiguration;
    }

    // TerminalEnclosure Configuration
    @Bean
    public TerminalEnclosureDAO terminalEnclosureDAO(){
        return new TerminalEnclosureDAO();
    }

    @Bean
    public TerminalEnclosureDAOConfiguration terminalEnclosureDAOConfiguration(TerminalEnclosureDAO dao, @Value("${batch.loader.terminalEnclosure.chunksize}") int chunkSize) throws PipelineException {
        TerminalEnclosureDAOConfiguration configuration = new TerminalEnclosureDAOConfiguration(dao, chunkSize);
        configuration.init();

        return configuration;
    }

    // Building Configuration
    @Bean
    public BuildingDAO buildingDAO(){
        return new BuildingDAO();
    }

    @Bean
    public BuildingDAOConfiguration buildingDAOConfiguration(BuildingDAO dao, @Value("${batch.loader.building.chunksize}") int chunkSize) throws PipelineException {
        BuildingDAOConfiguration configuration = new BuildingDAOConfiguration(dao, chunkSize);
        configuration.init();

        return configuration;
    }

    // XygoAddress Configuration
    @Bean
    public XygoAddressDAO xygoAddressDAO(){
        return new XygoAddressDAO();
    }

    @Bean
    public XygoAddressDAOConfiguration xygoAddressDAOConfiguration(XygoAddressDAO dao, @Value("${batch.loader.xygoAddress.chunksize}") int chunkSize) throws PipelineException {
        XygoAddressDAOConfiguration configuration = new XygoAddressDAOConfiguration(dao, chunkSize);
        configuration.init();

        return configuration;
    }

    @Bean
    public Logger logger(@Value("${app.logging.path}") String path,
                         @Value("${app.logging.filename}") String filename,
                         @Value("${app.logging.pattern}") String pattern,
                         @Value("${app.logging.level}") String level) {

        return LoggerFactory.getLogger(path, filename, pattern, level);
    }

}
