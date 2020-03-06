package org.pureacc.app.vocabulary;

import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public final class Username {
	@Size(min = 8,
		  max = 32)
	@NotNull
	private final String value;

	private Username(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static Username of(String value) {
		return new Username(value);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Username username = (Username) o;
		return Objects.equals(value, username.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}

	@Override
	public String toString() {
		return value;
	}
}
