package infra.audit;

import static org.pureacc.app.vocabulary.annotation.Allow.Role.AUTHENTICATED;

import org.pureacc.app.vocabulary.Password;
import org.pureacc.app.vocabulary.annotation.Allow;

public interface TestCommand {

	@Allow(AUTHENTICATED)
	Response success(Request request);

	@Allow(AUTHENTICATED)
	Response failure(Request request);

	final class Request {
		private final String username;
		private final Password password;

		private Request(Builder builder) {
			username = builder.username;
			password = builder.password;
		}

		public String getUsername() {
			return username;
		}

		public Password getPassword() {
			return password;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public static final class Builder {
			private String username;
			private Password password;

			private Builder() {
			}

			public Builder withUsername(String username) {
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
		private final String username;
		private final Password password;

		private Response(Builder builder) {
			username = builder.username;
			password = builder.password;
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public static final class Builder {
			private String username;
			private Password password;

			private Builder() {
			}

			public Builder withUsername(String username) {
				this.username = username;
				return this;
			}

			public Builder withPassword(Password password) {
				this.password = password;
				return this;
			}

			public Response build() {
				return new Response(this);
			}
		}
	}
}
