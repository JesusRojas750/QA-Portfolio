	package com.skidata.codingtest.exception;
	
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.ControllerAdvice;
	import org.springframework.web.bind.annotation.ExceptionHandler;
	
	@ControllerAdvice
	public class GlobalExceptionHandler {
	//Implementation of a global exception handler for specific exceptions and return of appropriate HTTP status codes and error messages.
	    @ExceptionHandler(PersonNotFoundException.class)
	    public ResponseEntity<String> handlePersonNotFoundException(PersonNotFoundException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	    }
//	    this
	    @ExceptionHandler(NullPointerException.class)
	    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
	    	ex.printStackTrace();
	        return new ResponseEntity<>("An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleGenericException(Exception ex) {
	        return new ResponseEntity<>("An unexpected error occurred. Please contact support.", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	    
	}