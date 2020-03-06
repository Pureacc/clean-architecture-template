package application.factory;

import org.pureacc.app.domain.model.User;
import org.pureacc.app.domain.repository.UserRepository;

import application.objectmother.UserObjectMother;

public class Users {
	private final UserRepository userRepository;

	public Users(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User aUser() {
		return persist(UserObjectMother.aUser());
	}

	private User persist(User user) {
		return userRepository.save(user);
	}
}
