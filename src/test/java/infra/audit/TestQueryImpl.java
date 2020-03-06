package infra.audit;

import org.pureacc.app.vocabulary.Password;
import org.pureacc.app.vocabulary.annotation.Query;

import vocabulary.TestException;

@Query
public class TestQueryImpl implements TestQuery {
	@Override
	public Response success(Request request) {
		return Response.newBuilder()
				.withUsername("response-username")
				.withPassword(Password.of("response-password"))
				.build();
	}

	@Override
	public Response failure(Request request) {
		throw new TestException("An error occurred");
	}
}
