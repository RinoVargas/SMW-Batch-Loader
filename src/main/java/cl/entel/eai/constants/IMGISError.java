package cl.entel.eai.constants;

public enum IMGISError {

    ERROR_DB_UNAVAILABLE_DISCONNECTION(99, "Fallo al hacer desconexión con la base de datos."),
    ERROR_DB_NOT_CONNECTED(99, "No se pudo conectar a la base de datos."),
    ERROR_DB_UNKNOWN_ERROR(99, "Error al consultar a la base de datos."),
    ERROR_UNKNOWN_ERROR(99, "Error error desconocido en el sistema.");

    private int code;
    private String message;

    IMGISError(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
