package org.pureacc.app.infra.rest;

import org.pureacc.app.vocabulary.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class RestControllerAdvice {
	@ExceptionHandler(UserException.class)
	public ResponseEntity<String> userException(UserException userException) {
		return ResponseEntity.badRequest()
				.body(userException.getMessage());
	}
}
