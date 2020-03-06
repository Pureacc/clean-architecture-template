package org.pureacc.app.application.command;

import org.pureacc.app.application.api.CreateUser;
import org.pureacc.app.domain.model.User;
import org.pureacc.app.domain.repository.UserRepository;
import org.pureacc.app.domain.service.PasswordHasher;
import org.pureacc.app.vocabulary.annotation.Command;

@Command
class CreateUserCommand implements CreateUser {
	private final UserRepository userRepository;
	private final PasswordHasher passwordHasher;

	CreateUserCommand(UserRepository userRepository, PasswordHasher passwordHasher) {
		this.userRepository = userRepository;
		this.passwordHasher = passwordHasher;
	}

	@Override
	public Response execute(Request request) {
		User user = new User(request.getUsername(), request.getPassword(), passwordHasher);
		User savedUser = userRepository.save(user);
		return Response.newBuilder()
				.withUserId(savedUser.getId())
				.build();
	}
}
