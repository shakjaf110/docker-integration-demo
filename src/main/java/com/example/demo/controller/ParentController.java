package com.example.demo.controller;

import com.example.demo.exceptions.CustomException;
import com.example.demo.exceptions.exceptionMessages.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
