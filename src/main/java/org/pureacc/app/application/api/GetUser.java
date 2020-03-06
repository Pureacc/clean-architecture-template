package org.pureacc.app.application.api;

import static org.pureacc.app.vocabulary.annotation.Allow.Role.AUTHENTICATED;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.pureacc.app.vocabulary.UserId;
import org.pureacc.app.vocabulary.Username;
import org.pureacc.app.vocabulary.annotation.Allow;
import org.pureacc.app.vocabulary.annotation.SecuredResource;

public interface GetUser {
	@Allow(AUTHENTICATED)
	Response execute(Request request);

	final class Request {
		@SecuredResource
		@Valid
		@NotNull
		private final UserId userId;

		private Request(Builder builder) {
			userId = builder.userId;
		}

		public UserId getUserId() {
			return userId;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public static final class Builder {
			private UserId userId;

			private Builder() {
			}

			public Builder withUserId(UserId userId) {
				this.userId = userId;
				return this;
			}

			public Request build() {
				return new Request(this);
			}
		}
	}

	final class Response {
		private final Username username;

		private Response(Builder builder) {
			username = builder.username;
		}

		public Username getUsername() {
			return username;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public static final class Builder {
			private Username username;

			private Builder() {
			}

			public Builder withUsername(Username username) {
				this.username = username;
				return this;
			}

			public Response build() {
				return new Response(this);
			}
		}
	}
}
