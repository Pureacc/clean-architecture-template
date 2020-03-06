package org.pureacc.app.domain.model.snapshot;

import org.pureacc.app.vocabulary.Password;
import org.pureacc.app.vocabulary.UserId;
import org.pureacc.app.vocabulary.Username;

public final class UserSnapshot {
	private final UserId userId;
	private final Username username;
	private final Password password;

	private UserSnapshot(Builder builder) {
		userId = builder.userId;
		username = builder.username;
		password = builder.password;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static Builder newBuilder(UserSnapshot copy) {
		Builder builder = new Builder();
		builder.userId = copy.getUserId();
		builder.username = copy.getUsername();
		builder.password = copy.getPassword();
		return builder;
	}

	public UserId getUserId() {
		return userId;
	}

	public Username getUsername() {
		return username;
	}

	public Password getPassword() {
		return password;
	}

	public static final class Builder {
		private UserId userId;
		private Username username;
		private Password password;

		private Builder() {
		}

		public Builder withUserId(UserId userId) {
			this.userId = userId;
			return this;
		}

		public Builder withUsername(Username username) {
			this.username = username;
			return this;
		}

		public Builder withPassword(Password password) {
			this.password = password;
			return this;
		}

		public UserSnapshot build() {
			return new UserSnapshot(this);
		}
	}
}
