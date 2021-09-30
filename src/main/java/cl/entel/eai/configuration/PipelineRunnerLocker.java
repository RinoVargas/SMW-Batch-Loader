package cl.entel.eai.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component()
public class PipelineRunnerLocker {

    @Value("${batch.loader.xygoAddress.enabled}")
    private boolean enableForXygoAddress;

    @Value("${batch.loader.building.enabled}")
    private boolean enableForBuilding;

    @Value("${batch.loader.terminalEnclosure.enabled}")
    private boolean enableForTerminalEnclosure;

    @Value("${batch.loader.hub.enabled}")
    private boolean enableForHub;

    public PipelineRunnerLocker() { }

    public boolean isEnableForXygoAddress() {
        return enableForXygoAddress;
    }

    public boolean isEnableForBuilding() {
        return enableForBuilding;
    }

    public boolean isEnableForTerminalEnclosure() {
        return enableForTerminalEnclosure;
    }

    public boolean isEnableForHub() {
        return enableForHub;
    }
}
