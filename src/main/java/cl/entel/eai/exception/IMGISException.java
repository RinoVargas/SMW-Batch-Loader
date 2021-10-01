package cl.entel.eai.exception;

import cl.entel.eai.constants.DAOError;

public class IMGISException extends Exception{
    private final DAOError status;
    private String message;

    public IMGISException(DAOError status) {
        this.status = status;
    }

    public IMGISException(DAOError status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        if (this.message != null) {
            return String.format("%s. %s", this.status.getMessage(), this.message);
        }
        return this.status.getMessage();
    }
}
