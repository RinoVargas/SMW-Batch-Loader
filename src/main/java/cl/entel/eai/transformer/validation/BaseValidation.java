package cl.entel.eai.transformer.validation;

public class BaseValidation {

    public static boolean isParseableToDouble(String string) {
        if (string == null) {
            return false;
        }

        try {
            Double.parseDouble(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
