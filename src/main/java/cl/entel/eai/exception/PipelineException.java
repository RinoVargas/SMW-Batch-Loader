package cl.entel.eai.exception;

import cl.entel.eai.constants.PipelineError;

public class PipelineException extends Exception{
    private final PipelineError status;
    private String message;

    public PipelineException(PipelineError status) {
        this.status = status;
    }

    public PipelineException(PipelineError status, String message) {
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
