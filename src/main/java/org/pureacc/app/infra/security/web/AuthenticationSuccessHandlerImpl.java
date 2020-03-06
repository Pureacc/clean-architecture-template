package org.pureacc.app.infra.security.web;

import static java.lang.String.format;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pureacc.app.domain.service.Time;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
	private static final DateTimeFormatter COOKIE_DATE_FORMATTER = DateTimeFormatter.ofPattern(
			"EEE, dd MMM yyyy HH:mm:ss 'GMT'")
			.withLocale(Locale.ENGLISH);
	private static final Duration SESSION_DURATION = Duration.ofHours(4);

	private final CryptoService cryptoService;
	private final Time time;

	AuthenticationSuccessHandlerImpl(CryptoService cryptoService, Time time) {
		this.cryptoService = cryptoService;
		this.time = time;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

		List<String> headerValues = new ArrayList<>();

		String cookieValue = userDetails.getId() + ":";
		Instant now = time.now();
		cookieValue += now.plus(SESSION_DURATION)
				.getEpochSecond();

		String encryptedCookieValue = cryptoService.encrypt(cookieValue);
		headerValues.add(AuthCookieFilter.COOKIE_NAME + "=" + encryptedCookieValue);

		long maxAgeInSeconds = SESSION_DURATION.getSeconds();
		OffsetDateTime expiryDate = now.plusSeconds(maxAgeInSeconds)
				.atOffset(ZoneOffset.UTC);
		headerValues.add("Max-Age=" + maxAgeInSeconds);
		headerValues.add("Expires=" + COOKIE_DATE_FORMATTER.format(expiryDate));
		headerValues.add("SameSite=Strict");
		headerValues.add("Path=/");
		headerValues.add("HttpOnly");

		response.addHeader("Set-Cookie", String.join("; ", headerValues));
		String responseFormat = "{\"userId\": %s, \"expires\": \"%s\"}";
		response.getWriter()
				.print(format(responseFormat, userDetails.getId(), expiryDate));
	}
}
