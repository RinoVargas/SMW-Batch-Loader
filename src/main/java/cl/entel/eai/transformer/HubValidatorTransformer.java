package cl.entel.eai.transformer;

import cl.entel.eai.model.Hub;
import cl.entel.eai.pipeline.transformer.Transformer;
import cl.entel.eai.transformer.validation.HubValidation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HubValidatorTransformer extends Transformer<Void, List<Hub>, List<Hub>> {

    public HubValidatorTransformer() { }

    @Override
    protected void init() { }

    @Override
    public List<Hub> process(List<Hub> input) {
        return input.stream().filter(HubValidation::validateHub).collect(Collectors.toList());
    }
}
