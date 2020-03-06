package org.pureacc.app.domain.service;

import org.pureacc.app.vocabulary.Password;

public interface PasswordHasher {
	Password hash(Password password);
}
