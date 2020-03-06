package infra.events.controller


import org.pureacc.app.domain.service.EventPublisher
import org.pureacc.app.infra.events.SpringEventPublisher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(classes = [SpringEventPublisher])
abstract class AbstractEventControllerSpec extends Specification {
    @Autowired
    protected EventPublisher eventPublisher
}
