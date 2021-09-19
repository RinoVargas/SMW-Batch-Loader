package cl.entel.eai.validation;

import cl.entel.eai.model.Hub;

public class HubValidation {

    public static boolean validateXCoordEG(String xCoordEG) {
        return BaseValidation.isParseableToDouble(xCoordEG);
    }

    public static boolean validateYCoordEG(String yCoordEG) {
        return BaseValidation.isParseableToDouble(yCoordEG);
    }

    public static boolean validateHub(Hub hub) {

        if (hub == null) {
            return false;
        }
        return HubValidation.validateXCoordEG(hub.getXCoordEg()) && HubValidation.validateYCoordEG(hub.getYCoordEg());
    }
}
