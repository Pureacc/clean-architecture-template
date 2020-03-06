package org.pureacc.app.infra.jpa.mapping;

import org.pureacc.app.domain.model.User;
import org.pureacc.app.domain.model.snapshot.UserSnapshot;
import org.pureacc.app.infra.jpa.model.UserEntity;
import org.pureacc.app.vocabulary.UserId;

public class ToUser {
	public static User map(UserEntity userEntity) {
		UserSnapshot userSnapshot = UserSnapshot.newBuilder()
				.withUserId(new UserId(userEntity.getId()))
				.withUsername(userEntity.getUsername())
				.withPassword(userEntity.getPassword())
				.build();
		return new User(userSnapshot);
	}
}
