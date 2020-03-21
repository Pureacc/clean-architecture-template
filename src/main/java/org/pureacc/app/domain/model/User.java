package org.pureacc.app.domain.model;

import org.pureacc.app.domain.model.snapshot.UserSnapshot;
import org.pureacc.app.domain.service.PasswordHasher;
import org.pureacc.app.vocabulary.Password;
import org.pureacc.app.vocabulary.UserId;
import org.pureacc.app.vocabulary.Username;

public class User {
	private UserId userId;
	private Username username;
	private Password password;

	public User(Username username, Password password, PasswordHasher passwordHasher) {
		this.username = username;
		this.password = passwordHasher.hash(password);
	}

	public User(UserSnapshot userSnapshot) {
		this.userId = userSnapshot.getUserId();
		this.username = userSnapshot.getUsername();
		this.password = userSnapshot.getPassword();
	}

	public UserSnapshot toSnapshot() {
		return UserSnapshot.newBuilder()
				.withUserId(userId)
				.withUsername(username)
				.withPassword(password)
				.build();
	}

	public UserId getId() {
		return userId;
	}

	public Username getUsername() {
		return username;
	}

	public Password getPassword() {
		return password;
	}
}
