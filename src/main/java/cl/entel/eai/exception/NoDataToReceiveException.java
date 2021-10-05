package cl.entel.eai.exception;

public class NoDataToReceiveException extends Exception {

    @Override
    public String getMessage() {
        return "No hay más datos por recibir";
    }
}
