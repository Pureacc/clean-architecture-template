package infra.events.controller;

import org.pureacc.app.domain.events.Event;
import org.pureacc.app.infra.security.web.AuthenticationService;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import vocabulary.TestException;

public class TestEventController {
	@EventListener
	public void handle(TestEvent testEvent) {
		SecurityContextHolder.getContext()
				.getAuthentication()
				.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.filter(a -> a.equals(AuthenticationService.Authority.SYSTEM.name()))
				.findAny()
				.orElseThrow(() -> new TestException("event listener was not called with the SYSTEM authority"));
		testEvent.handled = true;
	}

	public static final class TestEvent implements Event {
		public boolean handled = false;
	}
}
