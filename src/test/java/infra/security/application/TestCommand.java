package infra.security.application;

import static org.pureacc.app.vocabulary.annotation.Allow.Role.AUTHENTICATED;
import static org.pureacc.app.vocabulary.annotation.Allow.Role.SYSTEM;
import static org.pureacc.app.vocabulary.annotation.Allow.Role.UNAUTHENTICATED;

import org.pureacc.app.vocabulary.annotation.Allow;

public interface TestCommand {
	boolean allowNone();

	@Allow(UNAUTHENTICATED)
	boolean allowUnauthenticated();

	@Allow(AUTHENTICATED)
	boolean allowAuthenticated();

	@Allow({ UNAUTHENTICATED, AUTHENTICATED })
	boolean allowUnauthenticatedAndAuthenticated();

	@Allow(SYSTEM)
	boolean allowSystem();
}
