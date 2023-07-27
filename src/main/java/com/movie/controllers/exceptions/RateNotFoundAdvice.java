package com.movie.controllers.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ControllerAdvice
public class RateNotFoundAdvice {
	  @ResponseBody
	  @ExceptionHandler(RateNotFoundException.class)
	  @ResponseStatus(HttpStatus.NOT_FOUND)
	  String rateNotFoundHandler(RateNotFoundException ex) {
	    return ex.getMessage();
	  }
}
