package infra.security.application;

import static org.pureacc.app.vocabulary.annotation.Allow.Role.AUTHENTICATED;
import static org.pureacc.app.vocabulary.annotation.Allow.Role.SYSTEM;
import static org.pureacc.app.vocabulary.annotation.Allow.Role.UNAUTHENTICATED;

import org.pureacc.app.vocabulary.UserId;
import org.pureacc.app.vocabulary.annotation.Allow;
import org.pureacc.app.vocabulary.annotation.SecuredResource;

public interface TestQuery {
	boolean allowNone();

	@Allow(UNAUTHENTICATED)
	boolean allowUnauthenticated();

	@Allow(AUTHENTICATED)
	boolean allowAuthenticated();

	@Allow({ UNAUTHENTICATED, AUTHENTICATED })
	boolean allowUnauthenticatedAndAuthenticated();

	@Allow(SYSTEM)
	boolean allowSystem();

	@Allow(AUTHENTICATED)
	boolean securedResourceUser(TestUserRequest request);

	final class TestUserRequest {
		@SecuredResource
		private final UserId userId;

		private TestUserRequest(Builder builder) {
			userId = builder.userId;
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

			public TestUserRequest build() {
				return new TestUserRequest(this);
			}
		}
	}
}
