package cl.entel.eai.pipeline.transformer;

import cl.entel.eai.model.Building;
import cl.entel.eai.validation.BuildingValidation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuildingValidatorTransformer extends Transformer<Void, List<Building>, List<Building>> {

    public BuildingValidatorTransformer() { }

    @Override
    protected void init() { }

    @Override
    public List<Building> process(List<Building> input) {
        return input.stream().filter(BuildingValidation::validateBuilding).collect(Collectors.toList());
    }
}
