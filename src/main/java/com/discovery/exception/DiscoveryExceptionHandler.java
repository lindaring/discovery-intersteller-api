package com.discovery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DiscoveryExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PlanetImportFailed.class)
    public ResponseEntity<BusinessRuleException> handleImportDataMismatch(PlanetImportFailed e, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        BusinessRuleException apiException = new BusinessRuleException(e.getMessage(), status, status.value(), e.getClass().toString());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlanetNotFound.class)
    public ResponseEntity<BusinessRuleException> handleRequestDataNotFound(PlanetNotFound e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        BusinessRuleException apiException = new BusinessRuleException(e.getMessage(), status, status.value(), e.getClass().toString());
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<TechnicalException> handleMethodUnexpectedErrors(Exception e, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        TechnicalException apiException = new TechnicalException(e.getMessage(), status, status.value(), e.getClass().toString());
        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

}
