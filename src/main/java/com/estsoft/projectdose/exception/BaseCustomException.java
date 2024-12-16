package com.estsoft.projectdose.exception;

public class BaseCustomException extends RuntimeException {

	public BaseCustomException(String message) {
		super(message);
	}

	public BaseCustomException(String message, Throwable cause) {
		super(message, cause);
	}
}
