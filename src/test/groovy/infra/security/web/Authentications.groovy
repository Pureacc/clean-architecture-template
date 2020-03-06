package infra.security.web

import org.pureacc.app.domain.model.User
import org.pureacc.app.infra.security.web.AuthenticationService
import org.pureacc.app.infra.security.web.UserDetailsImpl
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder

import static java.util.Collections.emptyList
import static java.util.Collections.singletonList

class Authentications {
    static void authenticate(User user) {
        SecurityContextHolder.getContext()
                .setAuthentication(new TestingAuthenticationToken(new UserDetailsImpl(user), null, emptyList()));
    }

    static void authenticate(User user, AuthenticationService.Authority authority) {
        SecurityContextHolder.getContext()
                .setAuthentication(new TestingAuthenticationToken(new UserDetailsImpl(user), null,
                        singletonList(new SimpleGrantedAuthority(authority.name()))));
    }

    static void unauthenticate() {
        SecurityContextHolder.getContext()
                .setAuthentication(null);
    }
}
