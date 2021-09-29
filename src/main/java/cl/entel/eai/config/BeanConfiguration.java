package cl.entel.eai.config;

import cl.entel.eai.exception.IMGISException;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.pipeline.configuration.DAOConfiguration;
import cl.entel.eai.pipeline.configuration.HubDAOConfiguration;
import cl.entel.eai.dao.HubDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

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
}
