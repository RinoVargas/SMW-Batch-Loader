package cl.entel.eai.transformer;

import cl.entel.eai.model.TerminalEnclosure;
import cl.entel.eai.pipeline.transformer.Transformer;
import cl.entel.eai.transformer.validation.TerminalEnclosureValidation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TerminalEnclosureValidatorTransformer extends Transformer<Void, List<TerminalEnclosure>, List<TerminalEnclosure>> {

    public TerminalEnclosureValidatorTransformer() { }

    @Override
    protected void init() { }

    @Override
    public List<TerminalEnclosure> process(List<TerminalEnclosure> input) {
        return input.stream().filter(TerminalEnclosureValidation::validateTerminalEnclosure).collect(Collectors.toList());
    }
}
