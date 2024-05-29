package com.B2B.SP.user.exceptions;

import com.B2B.SP.user.exceptions.customexceptions.BadRequestException;
import com.B2B.SP.user.exceptions.customexceptions.UserNotFoundException;
import com.B2B.SP.user.exceptions.errorresponses.UserErrorResponse;
import com.B2B.SP.user.exceptions.errorresponses.ValidationErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class UserRestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserRestExceptionHandler.class);

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<UserErrorResponse> handleUserNotFoundException(UserNotFoundException exception){
        // create a ProductErrorResponse
        UserErrorResponse error = new UserErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(exception.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        logger.error("UserNotFoundException. Status: {}. Message: {}. Timestamp: {}",
                error.getStatus(),
                error.getMessage(),
                error.getTimestamp());

        // return responseEntity
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<UserErrorResponse> handleBadRequestException(Exception exception) {

        // create a ProductErrorResponse
        UserErrorResponse error = new UserErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(exception.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        logger.error("BadRequestException. Status: {}. Message: {}. Timestamp: {}",
                error.getStatus(),
                error.getMessage(),
                error.getTimestamp());

        // return responseEntity
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    // Validation Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        logger.error("Validation exception", exception);

        return ResponseEntity.badRequest().body(new ValidationErrorResponse(HttpStatus.BAD_REQUEST, "Validation errors", errors));
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleUserServiceException(Exception exception){

        // create a ProductErrorResponse
        UserErrorResponse error = new UserErrorResponse();

        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(exception.getMessage());
        error.setTimestamp(System.currentTimeMillis());

        logger.error("UserServiceException. Status: {}. Message: {}. Timestamp: {}",
                error.getStatus(),
                error.getMessage(),
                error.getTimestamp());

        // return responseEntity
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
