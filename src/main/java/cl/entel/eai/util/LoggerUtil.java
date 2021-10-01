package cl.entel.eai.util;

import cl.entel.eai.constants.PipelineError;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggerUtil {

    public static String formatErrorMessage(PipelineError error, String message) {
        return String.format("%s. %s", error.getMessage(), message);
    }

    public static String formatException(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
