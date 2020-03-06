package application.objectmother;

import org.pureacc.app.domain.model.User;
import org.pureacc.app.domain.model.snapshot.UserSnapshot;
import org.pureacc.app.vocabulary.Password;
import org.pureacc.app.vocabulary.UserId;
import org.pureacc.app.vocabulary.Username;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserObjectMother {
	public static final String RAW_PASSWORD = "password";

	public static User aUser() {
		return new User(defaultUserSnapshot().build());
	}

	private static UserSnapshot.Builder defaultUserSnapshot() {
		return UserSnapshot.newBuilder()
				.withUserId(UserId.of(1L))
				.withUsername(Username.of("John Doe"))
				.withPassword(Password.of("{bcrypt}" + new BCryptPasswordEncoder().encode(RAW_PASSWORD)));
	}
}
