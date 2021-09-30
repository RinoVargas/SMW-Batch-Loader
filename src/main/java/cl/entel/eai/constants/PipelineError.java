package cl.entel.eai.constants;

public enum PipelineError {

    ERROR_PIPELINE_READER(99, "Ocurrió un error durante la lectura de los datos"),
    ERROR_PIPELINE_WRITER(99, "Ocurrió un error durante la escritura de los datos"),
    ERROR_PIPELINE_DB_CLOSE_CONNECTION(99, "Ocurrió un error al cerrar las conexión a base de datos");

    private int code;
    private String message;

    PipelineError(int code, String message){
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
