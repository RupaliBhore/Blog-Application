package com.codewithrupaliblog.expections;

public class ApiException extends RuntimeException{
	
	
	public ApiException(String message) {
		super(message);

	}

	public ApiException() {
		super();

	}

}
