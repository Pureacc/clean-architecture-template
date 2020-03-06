package org.pureacc.app.infra.security.application;

import java.util.Arrays;
import java.util.List;

import org.pureacc.app.infra.security.AccessDeniedException;
import org.pureacc.app.infra.security.web.AuthenticationService;
import org.pureacc.app.vocabulary.annotation.Allow;
import org.pureacc.app.vocabulary.exception.SystemException;
import org.springframework.stereotype.Component;

@Component
class RoleSecurityControl {
	private final AuthenticationService authenticationService;

	RoleSecurityControl(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	public void check(Allow allow) {
		if (allow == null || !hasAtLeastOneRole(Arrays.asList(allow.value()))) {
			throw new AccessDeniedException();
		}
	}

	private boolean hasAtLeastOneRole(List<Allow.Role> roles) {
		return roles.stream()
				.anyMatch(this::hasRole);
	}

	private boolean hasRole(Allow.Role role) {
		switch (role) {
		case UNAUTHENTICATED:
			return !authenticationService.isAuthenticated();
		case AUTHENTICATED:
			return authenticationService.isAuthenticated();
		case SYSTEM:
			return authenticationService.hasAuthority(AuthenticationService.Authority.SYSTEM);
		default:
			throw new SystemException("Unknown role " + role);
		}
	}
}
