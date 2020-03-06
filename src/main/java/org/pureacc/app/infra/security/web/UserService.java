package org.pureacc.app.infra.security.web;

import org.pureacc.app.domain.model.User;
import org.pureacc.app.domain.repository.UserRepository;
import org.pureacc.app.vocabulary.UserId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
class UserService {
	private final UserRepository userRepository;

	UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public User get(UserId userId) {
		return userRepository.get(userId);
	}
}
