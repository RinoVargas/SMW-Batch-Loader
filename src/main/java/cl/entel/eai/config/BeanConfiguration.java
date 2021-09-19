package cl.entel.eai.config;

import cl.entel.eai.config.pipeline.HubDAOConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public HubDAOConfiguration hubDAOConfiguration(@Value("${batch.loader.hub.chunksize}") int chunkSize) {
        return new HubDAOConfiguration(chunkSize);
    }
}
