package infra.exceptionhandling

import org.pureacc.app.infra.exceptionhandling.ExceptionHandlingConfiguration
import org.pureacc.app.vocabulary.exception.UserException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.annotation.Import
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

@EnableAspectJAutoProxy
@ContextConfiguration(classes = ExceptionHandlingConfiguration)
@Import([TestCommandImpl, TestQueryImpl])
class ExceptionHandlingSpec extends Specification {
    @Autowired
    TestCommand testCommand
    @Autowired
    TestQuery testQuery

    @Unroll
    def "A domain exception with key #key thrown from a command is translated to a user exception"() {
        when: "I call a command that throws a domain exception with key #key"
        testCommand.domainException(key, params)

        then: "I receive a user exception with the translated message '#message'"
        UserException exception = thrown UserException
        exception.message == message

        where:
        key           | params || message
        "unknown.key" | null   || "unknown.key"
    }

    def "A system exception thrown from a command is translated to a user exception"() {
        when: "I call a command that throws a system exception"
        testCommand.systemException()

        then: "I receive a user exception with a generic error message"
        UserException exception = thrown UserException
        exception.message == "An unexpected error occurred"
    }

    @Unroll
    def "A domain exception with key #key thrown from a query is translated to a user exception"() {
        when: "I call a query that throws a domain exception with key #key"
        testQuery.domainException(key, params)

        then: "I receive a user exception with the translated message '#message'"
        UserException exception = thrown UserException
        exception.message == message

        where:
        key           | params || message
        "unknown.key" | null   || "unknown.key"
    }

    def "A system exception thrown from a query is translated to a user exception"() {
        when: "I call a query that throws a system exception"
        testQuery.systemException()

        then: "I receive a user exception with a generic error message"
        UserException exception = thrown UserException
        exception.message == "An unexpected error occurred"
    }
}
