package infra.security.application;

import org.pureacc.app.vocabulary.annotation.Query;

@Query
public class TestQueryImpl implements TestQuery {
	@Override
	public boolean allowNone() {
		return true;
	}

	@Override
	public boolean allowUnauthenticated() {
		return true;
	}

	@Override
	public boolean allowAuthenticated() {
		return true;
	}

	@Override
	public boolean allowUnauthenticatedAndAuthenticated() {
		return true;
	}

	@Override
	public boolean allowSystem() {
		return true;
	}

	@Override
	public boolean securedResourceUser(TestUserRequest request) {
		return true;
	}
}
