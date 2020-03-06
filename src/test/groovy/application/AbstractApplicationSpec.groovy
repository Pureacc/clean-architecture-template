package application

import application.conf.ApplicationSpecConfiguration
import application.factory.Users
import application.mock.TestEventPublisher
import application.stub.TestTime
import infra.security.web.Authentications
import org.pureacc.app.domain.service.DomainEventPublisher
import org.pureacc.app.domain.service.DomainTime
import org.pureacc.app.main.SpringAndReactApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import spock.lang.Specification

import javax.transaction.Transactional

@Transactional
@SpringBootTest(classes = SpringAndReactApplication.class)
@Import(ApplicationSpecConfiguration)
abstract class AbstractApplicationSpec extends Specification {
    @Autowired
    protected Users users
    @Autowired
    protected TestEventPublisher testEventPublisher
    @Autowired
    protected TestTime testTime

    void setup() {
        DomainEventPublisher.setPublisher(testEventPublisher)
        DomainTime.setTime(testTime);
        testEventPublisher.clear()
    }

    void cleanup() {
        Authentications.unauthenticate()
    }
}
