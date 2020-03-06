package infra.security.web


import application.stub.TestUserRepository
import org.pureacc.app.domain.model.User
import org.pureacc.app.domain.repository.UserRepository
import org.pureacc.app.main.SpringAndReactApplication
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.core.io.Resource
import org.springframework.http.*
import org.springframework.transaction.annotation.Transactional

import java.time.ZoneOffset
import java.util.regex.Pattern

import static org.springframework.http.MediaType.APPLICATION_JSON

@Transactional
@SpringBootTest(classes = SpringAndReactApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestController)
class WebSecuritySpec extends application.AbstractApplicationSpec {
    private static final Pattern COOKIE_PATTERN = Pattern.compile("X-authentication=.*?; Max-Age=14400; Expires=.*?; SameSite=Strict; Path=/; HttpOnly")

    @LocalServerPort
    private int port
    @Autowired
    private TestRestTemplate restTemplate
    @SpringBean
    private UserRepository userRepository = new TestUserRepository()
    @Value("classpath:web/user-register-request.json")
    Resource userRegisterRequest

    def "When I POST the login endpoint with valid credentials I receive an authentication cookie"() {
        given: "I am a user"
        User user = users.aUser()

        when: "I POST the login endpoint using my valid credentials"
        ResponseEntity response = login(user.username.value, application.objectmother.UserObjectMother.RAW_PASSWORD)

        then: "I receive an authentication cookie"
        String cookie = response.getHeaders().getFirst("Set-Cookie")
        COOKIE_PATTERN.matcher(cookie).matches()
        and: "I receive my user id and session expiry date"
        def expiryDate = testTime.now().atOffset(ZoneOffset.UTC).plusHours(4)
        response.body == "{\"userId\": ${user.id.value}, \"expires\": \"${expiryDate}\"}"
    }

    def "When I POST the login endpoint with an invalid username I get a 400 Bad Request with error message"() {
        when: "I POST the login endpoint using an invalid username"
        ResponseEntity response = login("unknown", application.objectmother.UserObjectMother.RAW_PASSWORD)

        then: "I get an error message"
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body == "Bad credentials"
    }

    def "When I POST the login endpoint with an invalid password I get a 400 Bad Request with error message"() {
        given: "I am a user"
        User user = users.aUser()

        when: "I POST the login endpoint using an invalid password"
        ResponseEntity response = login(user.username.value, "invalid")

        then: "I get an error message"
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body == "Bad credentials"
    }

    def "When I call a REST endpoint without having authenticated I receive a 401 Unauthorized"() {
        given: "I am not authenticated"

        when: "I call the REST endpoint"
        HttpHeaders headers = new HttpHeaders()
        HttpEntity<String> entity = new HttpEntity<String>(headers)
        ResponseEntity response = restTemplate.exchange("http://localhost:${port}/api/test", HttpMethod.GET, entity, String.class)

        then: "I receive a 401 Unauthorized"
        response.statusCode == HttpStatus.UNAUTHORIZED
    }

    def "When I call a REST endpoint having first authenticated I receive a 200 Ok"() {
        given: "I have valid credentials"
        User user = users.aUser()
        and: "I have authenticated and received an authentication cookie"
        ResponseEntity authenticationResponse = login(user.username.value, application.objectmother.UserObjectMother.RAW_PASSWORD)
        String authenticationCookie = authenticationResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE)

        when: "I call the REST endpoint using the authentication cookie"
        HttpHeaders headers = new HttpHeaders()
        headers.add("Cookie", authenticationCookie)
        HttpEntity<String> entity = new HttpEntity<>(headers)
        ResponseEntity response = restTemplate.exchange("http://localhost:${port}/api/test", HttpMethod.GET, entity, String.class)

        then: "I receive a 200 Ok"
        response.statusCode == HttpStatus.OK
        response.body == "Hello World!"
    }

    def "I can call the user registration endpoint without being authenticated"() {
        given: "I am not authenticated"

        when: "I call the REST endpoint"
        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(APPLICATION_JSON)
        HttpEntity<String> entity = new HttpEntity<String>(testutil.ResourceReader.asString(userRegisterRequest), headers)
        ResponseEntity response = restTemplate.exchange("http://localhost:${port}/api/user/register", HttpMethod.POST, entity, String.class)

        then: "I receive a 200 Ok"
        response.statusCode == HttpStatus.OK
    }

    private ResponseEntity login(String username, String password) {
        HttpHeaders headers = new HttpHeaders()
        HttpEntity<String> entity = new HttpEntity<>(headers)
        return restTemplate.exchange("http://localhost:${port}/login?username=${username}&password=${password}", HttpMethod.POST, entity, String.class)
    }
}
