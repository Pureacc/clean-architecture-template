package org.pureacc.app.vocabulary;

import java.util.Objects;

import javax.validation.constraints.NotBlank;

public final class Password {
	@NotBlank
	private final String value;

	private Password(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Password of(String value) {
		return new Password(value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Password password = (Password) o;
		return Objects.equals(value, password.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
