package org.pureacc.app.vocabulary;

import java.util.Objects;

import javax.validation.constraints.Positive;

public final class UserId {
	@Positive
	private final long value;

	public UserId(long value) {
		this.value = value;
	}

	public static UserId of(long value) {
		return new UserId(value);
	}

	public long getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserId userId = (UserId) o;
		return value == userId.value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value);
	}
}
