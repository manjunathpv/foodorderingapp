package com.upgrad.FoodOrderingApp.api.exception;

import com.upgrad.FoodOrderingApp.api.model.ErrorResponse;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(AuthorizationFailedException.class)
  public ResponseEntity<ErrorResponse> authorizationFailedException(AuthorizationFailedException exc, WebRequest request){
    return new ResponseEntity<ErrorResponse>(
            new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(CouponNotFoundException.class)
  public ResponseEntity<ErrorResponse> couponNotFoundException(CouponNotFoundException exc, WebRequest request){
    return new ResponseEntity<ErrorResponse>(
            new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(AddressNotFoundException.class)
  public ResponseEntity<ErrorResponse> addressNotFoundException(AddressNotFoundException exc, WebRequest request){
    return new ResponseEntity<ErrorResponse>(
            new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(PaymentMethodNotFoundException.class)
  public ResponseEntity<ErrorResponse> paymentMethodNotFoundException(PaymentMethodNotFoundException exc, WebRequest request){
    return new ResponseEntity<ErrorResponse>(
            new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RestaurantNotFoundException.class)
  public ResponseEntity<ErrorResponse> restaurantNotFoundException(RestaurantNotFoundException exc, WebRequest request){
    return new ResponseEntity<ErrorResponse>(
            new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ItemNotFoundException.class)
  public ResponseEntity<ErrorResponse> itemNotFoundException(ItemNotFoundException exc, WebRequest request){
    return new ResponseEntity<ErrorResponse>(
            new ErrorResponse().code(exc.getCode()).message(exc.getErrorMessage()), HttpStatus.NOT_FOUND);
  }
}
