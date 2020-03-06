package org.pureacc.app.domain.repository;

import java.util.Optional;

import org.pureacc.app.domain.model.User;
import org.pureacc.app.vocabulary.UserId;
import org.pureacc.app.vocabulary.Username;

public interface UserRepository {
	User get(UserId userId);

	User get(Username username);

	Optional<User> find(Username username);

	User save(User user);
}
