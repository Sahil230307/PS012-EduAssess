package com.eduassess.exam.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(IllegalArgumentException.class)
 public ResponseEntity<?> badRequest(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("error",e.getMessage()));}
 @ExceptionHandler(IllegalStateException.class)
 public ResponseEntity<?> conflict(IllegalStateException e){return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error",e.getMessage()));}
 @ExceptionHandler(SecurityException.class)
 public ResponseEntity<?> forbidden(SecurityException e){return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error",e.getMessage()));}
}
