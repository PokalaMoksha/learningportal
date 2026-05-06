package com.learning.common.exception;

import com.learning.common.response.ApiResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage>
           resourceNotFoundExceptionHandler(
           ResourceNotFoundException ex) {
        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(
                   response,
                   HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage>
           badApiRequestHandler(
           BadApiRequest ex) {
        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(
                   response,
                   HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponseMessage>
           accessDeniedHandler(
           AccessDeniedException ex) {
        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.FORBIDDEN)
                .build();
        return new ResponseEntity<>(
                   response,
                   HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponseMessage>
           userNotFoundHandler(
           UserNotFoundException ex) {
        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(
                   response,
                   HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ApiResponseMessage>
           employeeNotFoundHandler(
           EmployeeNotFoundException ex) {
        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(
                   response,
                   HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ManagerNotFoundException.class)
    public ResponseEntity<ApiResponseMessage>
           managerNotFoundHandler(
           ManagerNotFoundException ex) {
        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(
                   response,
                   HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CsmNotFoundException.class)
    public ResponseEntity<ApiResponseMessage>
           csmNotFoundHandler(
           CsmNotFoundException ex) {
        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message(ex.getMessage())
                .success(false)
                .status(HttpStatus.NOT_FOUND)
                .build();
        return new ResponseEntity<>(
                   response,
                   HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>
           handleValidationException(
           MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
          .getFieldErrors()
          .forEach(error -> errors.put(
                   error.getField(),
                   error.getDefaultMessage()));
        return new ResponseEntity<>(
                   errors,
                   HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseMessage>
           handleGenericException(Exception ex) {
        ApiResponseMessage response =
            ApiResponseMessage.builder()
                .message("Something went wrong: "
                         + ex.getMessage())
                .success(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return new ResponseEntity<>(
                   response,
                   HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
