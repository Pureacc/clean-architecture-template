package org.pureacc.app.infra.security.web;

import org.pureacc.app.domain.model.User;
import org.pureacc.app.domain.repository.UserRepository;
import org.pureacc.app.vocabulary.Username;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.find(Username.of(username))
				.orElseThrow(() -> new UsernameNotFoundException(username));
		return new UserDetailsImpl(user);
	}
}
