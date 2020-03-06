package infra.rest


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TestController)
@Import(TestController)
class RestControllerAdviceSpec extends AbstractControllerSpec {
    def "A user exception is translated to a 400 Bad Request including the error message in the response body"() {
        when: "I call an endpoint that throws in a user exception"
        def result = mvc.perform(get("/api/test"))

        then: "the HTTP status is 400 Bad Request"
        result.andExpect(status().isBadRequest())
        and: "the response body is the error message"
        result.andExpect(content().string("An error occurred"))
    }
}
