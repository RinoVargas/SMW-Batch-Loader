package cl.entel.eai.exception;

import cl.entel.eai.constants.IMGISError;

public class IMGISException extends Exception{
    private IMGISError status;
    private String message;

    public IMGISException(IMGISError status) {
        this.status = status;
    }

    public IMGISException(IMGISError status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        if (this.message != null) {
            return String.format("%s Detalles: %s", this.status.getMessage(), this.message);
        }
        return this.status.getMessage();
    }
}
