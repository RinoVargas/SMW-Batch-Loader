package cl.entel.eai.validation;

import cl.entel.eai.model.XygoAddress;

public class XygoAddressValidation {

    public static boolean validateLongitude(String longitude) {
        return BaseValidation.isParseableToDouble(longitude);
    }

    public static boolean validateLatitude(String latitude) {
        return BaseValidation.isParseableToDouble(latitude);
    }

    public static boolean validateXygoAddress(XygoAddress xygoAddress) {

        if (xygoAddress == null) {
            return false;
        }
        return XygoAddressValidation.validateLongitude(xygoAddress.getLongitude()) && XygoAddressValidation.validateLatitude(xygoAddress.getLatitude());
    }
}
