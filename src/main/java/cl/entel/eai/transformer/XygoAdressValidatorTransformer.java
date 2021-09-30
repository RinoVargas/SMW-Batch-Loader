package cl.entel.eai.transformer;

import cl.entel.eai.model.XygoAddress;
import cl.entel.eai.pipeline.transformer.Transformer;
import cl.entel.eai.transformer.validation.XygoAddressValidation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class XygoAdressValidatorTransformer extends Transformer<Void, List<XygoAddress>, List<XygoAddress>> {

    public XygoAdressValidatorTransformer() { }

    @Override
    protected void init() { }

    @Override
    public List<XygoAddress> process(List<XygoAddress> input) {
        return input.stream().filter(XygoAddressValidation::validateXygoAddress).collect(Collectors.toList());
    }
}
