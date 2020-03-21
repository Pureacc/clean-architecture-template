package infra.rest

import org.pureacc.app.application.api.CreateUser
import org.pureacc.app.application.api.GetUser
import org.pureacc.app.infra.rest.UserController
import org.pureacc.app.vocabulary.Password
import org.pureacc.app.vocabulary.UserId
import org.pureacc.app.vocabulary.Username
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.core.io.Resource
import spock.lang.Unroll

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = UserController)
@Unroll
class UserControllerSpec extends AbstractControllerSpec {
    @SpringBean
    CreateUser createUser = Mock(CreateUser)
    @SpringBean
    GetUser getUser = Mock(GetUser)
    @Value("classpath:web/user-register-request.json")
    Resource userRegisterRequest

    def "POST to #url with username '#username' and password '#password' creates a user and returns the user id"(String url, String username, String password, long userId) {
        when: "I create a user with username '#username' and password '#password'"
        def result = mvc.perform(post(url).contentType(APPLICATION_JSON).content(testutil.ResourceReader.asString(userRegisterRequest)))

        then: "A user is created"
        1 * createUser.execute({
            it.username == Username.of(username)
            it.password == Password.of(password)
        }) >> CreateUser.Response.newBuilder().withUserId(UserId.of(userId)).build()
        and: "HTTP status is 201"
        result.andExpect(status().isCreated())
        and: "I receive the user id"
        result.andExpect(jsonPath('$.userId').value(userId))

        where:
        url                  | username   | password  || userId
        "/api/user/register" | "John Doe" | "hunter2" || 123
    }

    def "GET to #url with user id '#userId' gets a user"(String url, long userId, String username) {
        when: "I get a user with user id '#userId'"
        def result = mvc.perform(get(url).param("userId", String.valueOf(userId)))

        then: "A user is returned"
        1 * getUser.execute({
            it.userId == UserId.of(userId)
        }) >> GetUser.Response.newBuilder().withUsername(Username.of(username)).build()
        and: "HTTP status is 200"
        result.andExpect(status().isOk())
        and: "I receive the user id"
        result.andExpect(jsonPath('$.userId').value(userId))
        and: "I receive the username"
        result.andExpect(jsonPath('$.username').value(username))

        where:
        url         | userId || username
        "/api/user" | 123    || "John Doe"
    }
}