package com.tomkasp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice(annotations=RestController.class)
public class RestControllerAdvice {

    static final Logger LOG = LoggerFactory.getLogger(RestControllerAdvice.class);

    protected ResponseEntity<RestApiError> createResponseEntity(RestApiError restApiError)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpStatus responseStatus = HttpStatus.valueOf(restApiError.getHttpStatus());
        ResponseEntity<RestApiError> result = new ResponseEntity<>(restApiError, headers, responseStatus);
        return result;
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestApiError> handleException(Exception e)
    {
        LOG.error("Api Error caused by exception", e);
        RestApiError restApiError = new RestApiError(RestApiHttpStatus.INTERNAL_SERVER_ERROR);
        restApiError.setApiCode(ApiErrorCodes.UNHANDLED_SERVER_EXCEPTION);
        restApiError.setUserMessage("The server encountered an error and could not complete the request");
        restApiError.setDeveloperMessage("The server encountred an unhandled exception and could not complete the request");

        return createResponseEntity(restApiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestApiError> handleException(MethodArgumentNotValidException e)
    {
        LOG.error("Api Error caused by exception", e);
        RestApiError restApiError = new RestApiError(RestApiHttpStatus.BAD_REQUEST);
        restApiError.setApiCode(ApiErrorCodes.INVALID_REQUEST_BODY);
        restApiError.setUserMessage("Error during object validation");
        restApiError.setDeveloperMessage("Validation error add more detailed message to the validation message");

        return createResponseEntity(restApiError);
    }
}
