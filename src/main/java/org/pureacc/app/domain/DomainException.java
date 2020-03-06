package org.pureacc.app.domain;

public class DomainException extends RuntimeException {
	private final String key;
	private final Object[] params;

	public DomainException(String key, Object... params) {
		this.key = key;
		this.params = params;
	}

	public String getKey() {
		return key;
	}

	public Object[] getParams() {
		return params;
	}
}
