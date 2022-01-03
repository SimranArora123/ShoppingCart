package com.tw.ShoppingCart.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(ProductAlreadyPresentException.class)
    public ResponseEntity<String>ItemAlreadyPresent(ProductAlreadyPresentException exception){
        return new ResponseEntity<String>("Product is Already Present", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotPresentException.class)
    public ResponseEntity<String>ItemNotPresent(ProductNotPresentException exception){
        return new ResponseEntity<String>("Product is Not Present", HttpStatus.NOT_FOUND);
    }

}
