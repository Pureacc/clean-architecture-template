package infra.security.application

import application.AbstractApplicationSpec
import org.pureacc.app.infra.security.AccessDeniedException
import org.pureacc.app.infra.security.web.AuthenticationService
import org.pureacc.app.vocabulary.UserId
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import spock.lang.Narrative

@Narrative("""
Validates resource-specific security. This type of security enables us to authorize use cases acting on particular resource instances. 
e.g. I can get the information of my own user, but not of other users.
""")
@Import([TestCommandImpl, TestQueryImpl])
class ApplicationResourceSecuritySpec extends AbstractApplicationSpec {
    @Autowired
    TestCommand command
    @Autowired
    TestQuery query
    @SpringBean
    AuthenticationService authenticationService = Stub()

    def "I cannot call a query method with resource security on UserId when I am authenticated as a different user"() {
        given: "I am authenticated as a user with id 1"
        authenticationService.isAuthenticated() >> true
        authenticationService.getAuthenticatedUserId() >> UserId.of(1L)

        when: "I call the method for a user with id 2"
        TestQuery.TestUserRequest request = TestQuery.TestUserRequest.newBuilder().withUserId(UserId.of(2L)).build()
        query.securedResourceUser(request)

        then: "The method throws access denied"
        thrown AccessDeniedException
    }

    def "I can call a query method with resource security on UserId when I am authenticated as the same user"() {
        given: "I am authenticated as a user with id 1"
        authenticationService.isAuthenticated() >> true
        authenticationService.getAuthenticatedUserId() >> UserId.of(1L)

        when: "I call the method for a user with id 1"
        TestQuery.TestUserRequest request = TestQuery.TestUserRequest.newBuilder().withUserId(UserId.of(1L)).build()
        boolean response = query.securedResourceUser(request)

        then: "The method returns successfully"
        response
    }
}
