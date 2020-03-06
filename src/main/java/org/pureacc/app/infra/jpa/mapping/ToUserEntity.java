package org.pureacc.app.infra.jpa.mapping;

import org.pureacc.app.domain.model.User;
import org.pureacc.app.domain.model.snapshot.UserSnapshot;
import org.pureacc.app.infra.jpa.model.UserEntity;

public class ToUserEntity {
	public static UserEntity map(User user) {
		UserSnapshot userSnapshot = user.toSnapshot();
		UserEntity userEntity = new UserEntity();
		if (userSnapshot.getUserId() != null) {
			userEntity.setId(userSnapshot.getUserId()
					.getValue());
		}
		userEntity.setUsername(userSnapshot.getUsername());
		userEntity.setPassword(userSnapshot.getPassword());
		return userEntity;
	}
}
