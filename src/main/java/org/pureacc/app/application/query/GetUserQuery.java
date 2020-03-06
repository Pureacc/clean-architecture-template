package org.pureacc.app.application.query;

import org.pureacc.app.application.api.GetUser;
import org.pureacc.app.domain.model.User;
import org.pureacc.app.domain.repository.UserRepository;
import org.pureacc.app.vocabulary.annotation.Query;

@Query
class GetUserQuery implements GetUser {
	private final UserRepository userRepository;

	GetUserQuery(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Response execute(Request request) {
		User user = userRepository.get(request.getUserId());
		return Response.newBuilder()
				.withUsername(user.getUsername())
				.build();
	}
}
