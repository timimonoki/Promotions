package promotions.exceptions;

public class WebServiceException extends RuntimeException {

    private ExceptionResponse exceptionResponse;

    public WebServiceException(){}

    public WebServiceException(String message){
        super(message);
    }

    public WebServiceException(ExceptionResponse exceptionResponse){
        this.exceptionResponse = exceptionResponse;
    }


    public WebServiceException(ExceptionResponse exceptionResponse,Throwable cause){
        super(cause);
        this.exceptionResponse = exceptionResponse;
    }

    public ExceptionResponse getExceptionResponse() {
        return exceptionResponse;
    }

    public void setExceptionResponse(ExceptionResponse exceptionResponse) {
        this.exceptionResponse = exceptionResponse;
    }
}
