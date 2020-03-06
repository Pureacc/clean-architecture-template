package infra.security.web

import org.pureacc.app.infra.security.web.AuthenticationService
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import spock.lang.Specification
import spock.lang.Unroll

import static java.util.Collections.emptyList
import static java.util.Collections.singletonList

class AuthenticationServiceSpec extends Specification {
    AuthenticationService authenticationService = new AuthenticationService()

    @Unroll
    def "Check for #authority returns true when I have the #authority authority"(AuthenticationService.Authority authority) {
        given: "I have the #authority authority"
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("username", "password",
                singletonList(new SimpleGrantedAuthority(authority.toString()))))

        expect: "Check for #authority returns true"
        authenticationService.hasAuthority(authority)

        where:
        authority << AuthenticationService.Authority.values()
    }

    @Unroll
    def "Check for #authority returns false when I do not have the #authority authority"(AuthenticationService.Authority authority) {
        given: "I do not have the #authority authority"
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("username", "password",
                emptyList()))

        expect: "Check for #authority returns false"
        !authenticationService.hasAuthority(authority)

        where:
        authority << AuthenticationService.Authority.values()
    }

    def "Check for authenticated returns true when I am authenticated"() {
        given: "I am authenticated"
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken("username", "password", emptyList()))

        expect:
        authenticationService.authenticated
    }

    def "Check for authenticated returns false when I am not authenticated"() {
        given: "I am not authenticated"
        SecurityContextHolder.clearContext()

        expect:
        !authenticationService.authenticated
    }
}
