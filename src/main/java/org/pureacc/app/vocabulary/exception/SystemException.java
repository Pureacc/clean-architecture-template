package org.pureacc.app.vocabulary.exception;

public class SystemException extends RuntimeException {
	public SystemException(String message) {
		super(message);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
