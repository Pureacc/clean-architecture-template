package org.pureacc.app.application.api;

import static org.pureacc.app.vocabulary.annotation.Allow.Role.UNAUTHENTICATED;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.pureacc.app.vocabulary.Password;
import org.pureacc.app.vocabulary.UserId;
import org.pureacc.app.vocabulary.Username;
import org.pureacc.app.vocabulary.annotation.Allow;

public interface CreateUser {
	@Allow(UNAUTHENTICATED)
	Response execute(@Valid Request request);

	final class Request {
		@Valid
		@NotNull
		private final Username username;
		@Valid
		@NotNull
		private final Password password;

		private Request(Builder builder) {
			username = builder.username;
			password = builder.password;
		}

		public Username getUsername() {
			return username;
		}

		public Password getPassword() {
			return password;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public static final class Builder {
			private Username username;
			private Password password;

			private Builder() {
			}

			public Builder withUsername(Username username) {
				this.username = username;
				return this;
			}

			public Builder withPassword(Password password) {
				this.password = password;
				return this;
			}

			public Request build() {
				return new Request(this);
			}
		}
	}

	final class Response {
		private final UserId userId;

		private Response(Builder builder) {
			userId = builder.userId;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public UserId getUserId() {
			return userId;
		}

		public static final class Builder {
			private UserId userId;

			private Builder() {
			}

			public Builder withUserId(UserId userId) {
				this.userId = userId;
				return this;
			}

			public Response build() {
				return new Response(this);
			}
		}
	}
}
