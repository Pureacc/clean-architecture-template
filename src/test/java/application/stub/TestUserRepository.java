package application.stub;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.pureacc.app.domain.model.User;
import org.pureacc.app.domain.model.snapshot.UserSnapshot;
import org.pureacc.app.domain.repository.UserRepository;
import org.pureacc.app.vocabulary.UserId;
import org.pureacc.app.vocabulary.Username;

import vocabulary.TestException;

public class TestUserRepository implements UserRepository {
	private Set<User> users = new HashSet<>();

	@Override
	public User get(UserId userId) {
		return users.stream()
				.filter(u -> userId.equals(u.getId()))
				.findFirst()
				.orElseThrow(() -> new TestException("Could not find user with id " + userId.getValue()));
	}

	@Override
	public User get(Username username) {
		return find(username).orElseThrow(
				() -> new TestException("Could not find user with username " + username.getValue()));
	}

	@Override
	public Optional<User> find(Username username) {
		return users.stream()
				.filter(u -> username.equals(u.getUsername()))
				.findFirst();
	}

	@Override
	public User save(User user) {
		UserSnapshot userSnapshot = UserSnapshot.newBuilder(user.toSnapshot())
				.withUserId(UserId.of(new Date().getTime()))
				.build();
		User saveUser = new User(userSnapshot);
		users.add(saveUser);
		return saveUser;
	}
}
