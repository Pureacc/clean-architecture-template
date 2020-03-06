package org.pureacc.app.infra.jpa.repository;

import java.util.Optional;

import org.pureacc.app.domain.model.User;
import org.pureacc.app.domain.repository.UserRepository;
import org.pureacc.app.infra.jpa.dao.UserJpaDao;
import org.pureacc.app.infra.jpa.mapping.ToUser;
import org.pureacc.app.infra.jpa.mapping.ToUserEntity;
import org.pureacc.app.infra.jpa.model.UserEntity;
import org.pureacc.app.vocabulary.UserId;
import org.pureacc.app.vocabulary.Username;
import org.springframework.stereotype.Component;

@Component
class UserJpaRepository implements UserRepository {
	private final UserJpaDao userJpaDao;

	UserJpaRepository(UserJpaDao userJpaDao) {
		this.userJpaDao = userJpaDao;
	}

	@Override
	public User get(UserId userId) {
		UserEntity userEntity = userJpaDao.getOne(userId.getValue());
		return ToUser.map(userEntity);
	}

	@Override
	public User get(Username username) {
		UserEntity userEntity = userJpaDao.findByUsername(username.getValue());
		return ToUser.map(userEntity);
	}

	@Override
	public Optional<User> find(Username username) {
		UserEntity userEntity = userJpaDao.findByUsername(username.getValue());
		return Optional.ofNullable(userEntity)
				.map(ToUser::map);
	}

	@Override
	public User save(User user) {
		UserEntity userEntity = ToUserEntity.map(user);
		return ToUser.map(userJpaDao.save(userEntity));
	}
}
