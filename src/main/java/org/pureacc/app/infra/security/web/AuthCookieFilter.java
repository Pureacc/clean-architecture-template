package org.pureacc.app.infra.security.web;

import java.io.IOException;
import java.time.Instant;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.pureacc.app.domain.model.User;
import org.pureacc.app.vocabulary.UserId;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
class AuthCookieFilter extends GenericFilterBean {
	final static String COOKIE_NAME = "X-authentication";

	private final UserService userService;
	private final CryptoService cryptoService;
	private final UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

	AuthCookieFilter(UserService userService, CryptoService cryptoService) {
		this.userService = userService;
		this.cryptoService = cryptoService;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

		Cookie[] cookies = httpServletRequest.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName()
						.equals(COOKIE_NAME)) {
					String decryptedCookieValue = this.cryptoService.decrypt(cookie.getValue());
					if (decryptedCookieValue != null) {
						int colonPos = decryptedCookieValue.indexOf(':');
						String appUserIdString = decryptedCookieValue.substring(0, colonPos);
						long expiresAtEpochSeconds = Long.valueOf(decryptedCookieValue.substring(colonPos + 1));

						if (Instant.now()
								.getEpochSecond() < expiresAtEpochSeconds) {
							try {
								User user = userService.get(UserId.of(Long.parseLong(appUserIdString)));
								UserDetailsImpl userDetails = new UserDetailsImpl(user);
								this.userDetailsChecker.check(userDetails);

								SecurityContextHolder.getContext()
										.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null,
												userDetails.getAuthorities()));
							} catch (UsernameNotFoundException | LockedException | DisabledException | AccountExpiredException | CredentialsExpiredException e) {
								// ignore this
							}
						}
					}
				}
			}
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}
}
