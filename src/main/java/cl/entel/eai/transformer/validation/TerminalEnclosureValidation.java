package cl.entel.eai.transformer.validation;

import cl.entel.eai.model.TerminalEnclosure;

public class TerminalEnclosureValidation {

    public static boolean validateXCoordEG(String xCoordEG) {
        return BaseValidation.isParseableToDouble(xCoordEG);
    }

    public static boolean validateYCoordEG(String yCoordEG) {
        return BaseValidation.isParseableToDouble(yCoordEG);
    }

    public static boolean validateTerminalEnclosure(TerminalEnclosure terminalEnclosure) {

        if (terminalEnclosure == null) {
            return false;
        }
        return TerminalEnclosureValidation.validateXCoordEG(terminalEnclosure.getXCoordEg()) && TerminalEnclosureValidation.validateYCoordEG(terminalEnclosure.getYCoordEg());
    }
}
