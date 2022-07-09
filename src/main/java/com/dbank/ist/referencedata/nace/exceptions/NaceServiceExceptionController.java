package com.dbank.ist.referencedata.nace.exceptions;

import org.hibernate.id.IdentifierGenerationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class NaceServiceExceptionController {

    //in case the requested record is not present in our Database
    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<?> noSuchElementExceptionHandler(NoSuchElementException exception) {
        return new ResponseEntity<>("Details not found", HttpStatus.NOT_FOUND);
    }

    //in case the sent record is without the mandatory field "Order"
    @ExceptionHandler(value = IdentifierGenerationException.class)
    public ResponseEntity<?> identifierGenerationExceptionHandler(IdentifierGenerationException exception) {
        return new ResponseEntity<>("The \"Order\" is a mandatory field", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<?> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException exception) {
        return new ResponseEntity<>(String.format("DataIntegrityViolation due to '%s'", exception.getRootCause()), HttpStatus.BAD_REQUEST);
    }
}
