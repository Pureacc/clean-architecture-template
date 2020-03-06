package org.pureacc.app.infra.security.web;

import org.pureacc.app.vocabulary.UserId;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {
	private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

	public boolean isAuthenticated() {
		Authentication authentication = getAuthentication();
		return authentication != null && authentication.isAuthenticated() && !isAnonymous(authentication);
	}

	public UserId getAuthenticatedUserId() {
		Authentication authentication = getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		return UserId.of(userDetails.getId());
	}

	public boolean hasAuthority(Authority authority) {
		Authentication authentication = getAuthentication();
		return authentication != null && authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch((a) -> a.equals(authority.name()));
	}

	private boolean isAnonymous(Authentication authentication) {
		return authentication.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.anyMatch(ROLE_ANONYMOUS::equals);
	}

	private Authentication getAuthentication() {
		return SecurityContextHolder.getContext()
				.getAuthentication();
	}

	public enum Authority {
		SYSTEM
	}
}
