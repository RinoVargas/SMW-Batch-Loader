package cl.entel.eai;

import ch.qos.logback.classic.Logger;
import cl.entel.eai.configuration.PipelineRunnerLocker;
import cl.entel.eai.exception.PipelineException;
import cl.entel.eai.runner.BuildingDAOPipelineRunner;
import cl.entel.eai.runner.HubDAOPipelineRunner;
import cl.entel.eai.runner.TerminalEnclosureDAOPipelineRunner;
import cl.entel.eai.runner.XygoAddressDAOPipelineRunner;
import cl.entel.eai.util.LoggerUtil;
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

	@Autowired
	Logger logger;

	public static void main(String[] args) {
		SpringApplication.run(SmwBatchLoaderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		PipelineRunnerLocker locker = context.getBean(PipelineRunnerLocker.class);

		// Hub Runner
		try {
			if (locker.isEnableForHub()) {
				HubDAOPipelineRunner hubDAOPipelineRunner = context.getBean(HubDAOPipelineRunner.class);
				hubDAOPipelineRunner.run();
			}
		} catch (PipelineException e) {
			logger.error("La carga de Hubs se ha interrumpido por un error...");
			logger.error(e.getMessage());
			logger.error(LoggerUtil.formatException(e));
		}

		// Terminal Enclosure Runner
		try {
			if (locker.isEnableForTerminalEnclosure()) {
				TerminalEnclosureDAOPipelineRunner terminalEnclosureDAOPipelineRunner = context.getBean(TerminalEnclosureDAOPipelineRunner.class);
				terminalEnclosureDAOPipelineRunner.run();
			}
		} catch (PipelineException e) {
			logger.error("La carga de Terminal Enclosure's se ha interrumpido por un error...");
			logger.error(e.getMessage());
			logger.error(LoggerUtil.formatException(e));
		}

		// Building Runner
		try {
			if (locker.isEnableForBuilding()) {
				BuildingDAOPipelineRunner buildingDAOPipelineRunner = context.getBean(BuildingDAOPipelineRunner.class);
				buildingDAOPipelineRunner.run();
			}
		} catch (PipelineException e) {
			logger.error("La carga de Buildings se ha interrumpido por un error...");
			logger.error(e.getMessage());
			logger.error(LoggerUtil.formatException(e));
		}

		// XygoAddress Runner
		try {
			if (locker.isEnableForXygoAddress()) {
				XygoAddressDAOPipelineRunner xygoAddressDAOPipelineRunner = context.getBean(XygoAddressDAOPipelineRunner.class);
				xygoAddressDAOPipelineRunner.run();
			}
		} catch (PipelineException e) {
			logger.error("La carga de Direcciones se ha interrumpido por un error...");
			logger.error(e.getMessage());
			logger.error(LoggerUtil.formatException(e));
		}
	}
}
