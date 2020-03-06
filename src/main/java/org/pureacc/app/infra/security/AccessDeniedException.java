package org.pureacc.app.infra.security;

public class AccessDeniedException extends RuntimeException {
	public AccessDeniedException() {
		super("Access is denied");
	}
}
