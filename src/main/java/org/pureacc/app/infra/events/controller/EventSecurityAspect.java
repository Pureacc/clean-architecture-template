package org.pureacc.app.infra.events.controller;

import static java.util.Collections.singletonList;
import static org.pureacc.app.infra.security.web.AuthenticationService.Authority.SYSTEM;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
class EventSecurityAspect {
	@Around("@annotation(org.springframework.context.event.EventListener)")
	public Object aspect(ProceedingJoinPoint joinPoint) throws Throwable {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		try {
			authenticateSystemUser();
			return joinPoint.proceed();
		} finally {
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
		}
	}

	private void authenticateSystemUser() {
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("system", "system",
						singletonList(new SimpleGrantedAuthority(SYSTEM.name()))));
	}
}
