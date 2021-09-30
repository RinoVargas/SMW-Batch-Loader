package cl.entel.eai;

import cl.entel.eai.runner.BuildingDAOPipelineRunner;
import cl.entel.eai.runner.HubDAOPipelineRunner;
import cl.entel.eai.runner.TerminalEnclosureDAOPipelineRunner;
import cl.entel.eai.runner.XygoAddressDAOPipelineRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

@PropertySource("file:${environment}/smw-batch-loader.properties")
@SpringBootApplication
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

		// Terminal Enclosure Runner
		TerminalEnclosureDAOPipelineRunner terminalEnclosureDAOPipelineRunner = context.getBean(TerminalEnclosureDAOPipelineRunner.class);
		terminalEnclosureDAOPipelineRunner.run();

		// Building Runner
		BuildingDAOPipelineRunner buildingDAOPipelineRunner = context.getBean(BuildingDAOPipelineRunner.class);
		buildingDAOPipelineRunner.run();

		// XygoAddress Runner
		XygoAddressDAOPipelineRunner xygoAddressDAOPipelineRunner = context.getBean(XygoAddressDAOPipelineRunner.class);
		xygoAddressDAOPipelineRunner.run();
	}
}
