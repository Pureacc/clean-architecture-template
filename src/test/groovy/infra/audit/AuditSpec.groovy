package infra.audit

import infra.security.web.Authentications
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.core.LogEvent
import org.apache.logging.log4j.core.Logger
import org.apache.logging.log4j.core.appender.AbstractAppender
import org.apache.logging.log4j.core.config.Property
import org.pureacc.app.domain.model.User
import org.pureacc.app.infra.audit.Slf4jAuditor
import org.pureacc.app.vocabulary.Password
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import spock.lang.Narrative
import vocabulary.TestException

@Narrative("""
Audit Command and Query executions. Audited information includes actor, use case name, method name, arguments, response.
Sensitive attributes (e.g. Password) of arguments or response are not included in the audit.
""")
@Import([TestCommandImpl, TestQueryImpl])
class AuditSpec extends application.AbstractApplicationSpec {
    @Autowired
    TestCommand testCommand
    @Autowired
    TestQuery testQuery

    private MockedAppender mockedAppender
    private Logger logger

    void setup() {
        mockedAppender = new MockedAppender()
        logger = (Logger) LogManager.getLogger(Slf4jAuditor.class)
        logger.addAppender(mockedAppender)
        logger.setLevel(Level.INFO)
    }

    void cleanup() {
        logger.removeAppender(mockedAppender)
    }

    def "The execution of a command that succeeds is audited"() {
        given: "I am an authenticated user"
        User user = users.aUser()
        Authentications.authenticate(user)

        when: "I execute a command that succeeds"
        def request = TestCommand.Request.newBuilder()
                .withUsername("johndoe")
                .withPassword(Password.of("hunter2"))
                .build()
        testCommand.success(request)

        then: "the action is audited"
        def logEvents = mockedAppender.logEvents
        logEvents.size() == 1
        def logEvent = logEvents.get(0)
        logEvent.level == Level.INFO
        def message = logEvent.message.formattedMessage
        and: "the audit signifies success"
        message.contains("SUCCESS")
        and: "the audit contains the actor"
        message.contains("John Doe")
        and: "the audit contains the use case name and method"
        message.contains("TestCommandImpl::success")
        and: "the audit contains the arguments"
        message.contains("johndoe")
        and: "the audit does not contain sensitive arguments"
        !message.contains("hunter2")
        and: "the audit contains the response attributes"
        message.contains("response-username")
        and: "the audit does not contain sensitive response attributes"
        !message.contains("response-password")
    }

    def "The execution of a command that fails is audited"() {
        given: "I am an authenticated user"
        User user = users.aUser()
        Authentications.authenticate(user)

        when: "I execute a command that fails"
        def request = TestCommand.Request.newBuilder()
                .withUsername("johndoe")
                .withPassword(Password.of("hunter2"))
                .build()
        testCommand.failure(request)

        then: "the action failed"
        thrown TestException
        and: "the action is audited"
        def logEvents = mockedAppender.logEvents
        logEvents.size() == 1
        def logEvent = logEvents.get(0)
        logEvent.level == Level.INFO
        def message = logEvent.message.formattedMessage
        and: "the audit signifies failure"
        message.contains("FAILURE")
        and: "the audit contains the actor"
        message.contains("John Doe")
        and: "the audit contains the use case name and method"
        message.contains("TestCommandImpl::failure")
        and: "the audit contains the arguments"
        message.contains("johndoe")
        and: "the audit does not contain sensitive arguments"
        !message.contains("hunter2")
        and: "the audit contains the error message"
        message.contains("An error occurred")
    }

    def "The execution of a query that succeeds is audited"() {
        given: "I am an authenticated user"
        User user = users.aUser()
        Authentications.authenticate(user)

        when: "I execute a query that succeeds"
        def request = TestQuery.Request.newBuilder()
                .withUsername("johndoe")
                .withPassword(Password.of("hunter2"))
                .build()
        testQuery.success(request)

        then: "the action is audited"
        def logEvents = mockedAppender.logEvents
        logEvents.size() == 1
        def logEvent = logEvents.get(0)
        logEvent.level == Level.INFO
        def message = logEvent.message.formattedMessage
        and: "the audit signifies success"
        message.contains("SUCCESS")
        and: "the audit contains the actor"
        message.contains("John Doe")
        and: "the audit contains the use case name and method"
        message.contains("TestQueryImpl::success")
        and: "the audit contains the arguments"
        message.contains("johndoe")
        and: "the audit does not contain sensitive arguments"
        !message.contains("hunter2")
        and: "the audit contains the response attributes"
        message.contains("response-username")
        and: "the audit does not contain sensitive response attributes"
        !message.contains("response-password")
    }

    def "The execution of a query that fails is audited"() {
        given: "I am an authenticated user"
        User user = users.aUser()
        Authentications.authenticate(user)

        when: "I execute a query that fails"
        def request = TestQuery.Request.newBuilder()
                .withUsername("johndoe")
                .withPassword(Password.of("hunter2"))
                .build()
        testQuery.failure(request)

        then: "the action failed"
        thrown TestException
        and: "the action is audited"
        def logEvents = mockedAppender.logEvents
        logEvents.size() == 1
        def logEvent = logEvents.get(0)
        logEvent.level == Level.INFO
        def message = logEvent.message.formattedMessage
        and: "the audit signifies failure"
        message.contains("FAILURE")
        and: "the audit contains the actor"
        message.contains("John Doe")
        and: "the audit contains the use case name and method"
        message.contains("TestQueryImpl::failure")
        and: "the audit contains the arguments"
        message.contains("johndoe")
        and: "the audit does not contain sensitive arguments"
        !message.contains("hunter2")
        and: "the audit contains the error message"
        message.contains("An error occurred")
    }

    private static class MockedAppender extends AbstractAppender {
        List<LogEvent> logEvents = new ArrayList<>()

        MockedAppender() {
            super("MockedAppender", null, null, true, Property.EMPTY_ARRAY)
        }

        @Override
        void append(LogEvent event) {
            logEvents.add(event)
        }
    }
}
