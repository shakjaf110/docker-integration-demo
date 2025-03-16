package com.example.demo.controller;

import com.example.demo.exceptions.ApiError;
import com.example.demo.exceptions.CustomException;
import com.example.demo.exceptions.exceptionMessages.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;


import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class ParentController {

    @ExceptionHandler
    public ResponseEntity<ErrorMessage> throwException(CustomException customException)
    {
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setMessage(customException.getMessage());
        errorMessage.setStatusCode(HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessage>  throwException(Exception exception)
    {
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setMessage(exception.getMessage());
        errorMessage.setStatusCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<ErrorMessage>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<String> handleControllerException(AccessDeniedException ex) {
        return new ResponseEntity<>(ex.getMessage(), UNAUTHORIZED);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        ApiError error = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .code("VALIDATION_ERROR")
                .message("Validation Error " + ex.getMessage())
                .path(getPath(request))
                .build();
        error.addValidationErrors(ex.getConstraintViolations());
        return buildResponseEntity(error);

    }

    /**
     * Build {@link ResponseEntity} with given {@link ApiError}.
     *
     * @param apiError {@link ApiError} to build {@link ResponseEntity}
     * @return {@link ResponseEntity} from given {@link ApiError}
     */
    public static ResponseEntity<Object> buildResponseEntity(final ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    /**
     * Extract path of request.
     *
     * @param request {@link WebRequest} from which will be extracted path
     * @return Path string
     */
    public static String getPath(final WebRequest request) {
        return request.getDescription(false)
                .substring(4);
    }
}
