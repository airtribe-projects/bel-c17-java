package org.airtribe.LearnerManagementSystemBelC17.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(LearnerNotFoundException.class)
  public ResponseEntity<String> handleLearnerNotFoundException(LearnerNotFoundException exception) {
    return ResponseEntity.status(404).body(exception.getMessage());
  }
}
