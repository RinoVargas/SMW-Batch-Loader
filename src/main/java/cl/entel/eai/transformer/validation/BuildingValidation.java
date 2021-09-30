package cl.entel.eai.transformer.validation;

import cl.entel.eai.model.Building;

public class BuildingValidation {

    public static boolean validateXCoordEG(String xCoordEG) {
        return BaseValidation.isParseableToDouble(xCoordEG);
    }

    public static boolean validateYCoordEG(String yCoordEG) {
        return BaseValidation.isParseableToDouble(yCoordEG);
    }

    public static boolean validateBuilding(Building building) {

        if (building == null) {
            return false;
        }
        return BuildingValidation.validateXCoordEG(building.getXCoordEg()) && BuildingValidation.validateYCoordEG(building.getYCoordEg());
    }
}
