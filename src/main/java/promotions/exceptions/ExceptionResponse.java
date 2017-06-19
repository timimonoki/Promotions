package promotions.exceptions;

import org.eclipse.jetty.websocket.api.StatusCode;

public class ExceptionResponse {

    private ExceptionCode exceptionCode;
    private String message;

    public ExceptionResponse(){}

    public ExceptionResponse(ExceptionCode exceptionCode, String message){
        this.exceptionCode = exceptionCode;
        this.message = message;
    }

    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(ExceptionCode exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
