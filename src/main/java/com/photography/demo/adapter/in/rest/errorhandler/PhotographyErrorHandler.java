package com.photography.demo.adapter.in.rest.errorhandler;

import com.photography.demo.application.exception.PhotographyBaseException;
import com.photography.demo.application.exception.PhotographyNotExistException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PhotographyErrorHandler {

  /**
   * Results of constraints @Valid annotations
   */
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return errors;
  }

  @ExceptionHandler(PhotographyBaseException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  PhotographyErrorResponse handlePhotographyBaseExceptionException(PhotographyBaseException photographyBaseException) {
    return new PhotographyErrorResponse(photographyBaseException);
  }

}
