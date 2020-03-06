package org.pureacc.app.infra.security.web;

import org.pureacc.app.domain.service.PasswordHasher;
import org.pureacc.app.vocabulary.Password;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
class BCryptPasswordHasher implements PasswordHasher {
	private static final String ALGORITHM = "{bcrypt}";
	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	public Password hash(Password password) {
		String hash = bCryptPasswordEncoder.encode(password.getValue());
		return Password.of(ALGORITHM + hash);
	}
}
