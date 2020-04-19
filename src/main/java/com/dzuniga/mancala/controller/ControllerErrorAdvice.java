package com.dzuniga.mancala.controller;

import com.dzuniga.mancala.business.move.validations.exceptions.MoveValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/** Maps app exceptions to proper Http status and responses */
@RestControllerAdvice
public class ControllerErrorAdvice extends ResponseEntityExceptionHandler {

  /**
   * handles the {@link MoveValidationException} converting the response to Http 400 bad request
   *
   * @param e the MoveValidationException captured
   * @param request the request object
   * @return ResponseEntity with the {@link RestError} and Status
   */
  @ExceptionHandler(MoveValidationException.class)
  public ResponseEntity<Object> handleMoveValidationException(
      MoveValidationException e, WebRequest request) {
    RestError restError =
        RestError.builder().message(e.getMessage()).status(HttpStatus.BAD_REQUEST).build();
    return handleExceptionInternal(e, restError, new HttpHeaders(), restError.getStatus(), request);
  }
}
