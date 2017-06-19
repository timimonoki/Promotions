package promotions.exceptions;

public class CurrentCatalogException extends WebServiceException {

    public CurrentCatalogException(ExceptionResponse exceptionResponse){
        super(exceptionResponse);
    }

}
