package org.pureacc.app.infra.security.web;

import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@EnableWebSecurity
@Component
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final AuthenticationSuccessHandler authenticationSuccessHandler;
	private final AuthenticationFailureHandler authenticationFailureHandler;
	private final GenericFilterBean authCookieFilter;

	WebSecurityConfiguration(AuthenticationSuccessHandler authenticationSuccessHandler,
			AuthenticationFailureHandler authenticationFailureHandler, GenericFilterBean authCookieFilter) {
		this.authenticationSuccessHandler = authenticationSuccessHandler;
		this.authenticationFailureHandler = authenticationFailureHandler;
		this.authCookieFilter = authCookieFilter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
		.headers().contentSecurityPolicy("script-src 'self'; object-src 'none'; base-uri 'self'").and()
			.and()
		.formLogin()
		.successHandler(authenticationSuccessHandler)
		.failureHandler(authenticationFailureHandler)
		.permitAll()
			.and()
		.logout()
		.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
		.deleteCookies(AuthCookieFilter.COOKIE_NAME)
		.permitAll()
			.and()
		.authorizeRequests()
		.antMatchers("/api/user/register").permitAll()
		.anyRequest().authenticated()
			.and()
		.exceptionHandling()
		.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
			.and()
		.addFilterBefore(this.authCookieFilter, UsernamePasswordAuthenticationFilter.class)
		.csrf().disable();
		// @formatter:on
	}
}
