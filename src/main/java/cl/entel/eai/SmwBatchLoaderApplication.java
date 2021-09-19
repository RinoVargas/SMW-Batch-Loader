package cl.entel.eai;

import cl.entel.eai.runner.HubDAOPipelineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("file:${environment}/smw-batch-loader.properties")
public class SmwBatchLoaderApplication implements CommandLineRunner {

	@Autowired
	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(SmwBatchLoaderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Hub Runner
		HubDAOPipelineRunner hubDAOPipelineRunner = context.getBean(HubDAOPipelineRunner.class);
		hubDAOPipelineRunner.run();
	}
}
