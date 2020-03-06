package org.pureacc.app.infra.events;

import org.pureacc.app.domain.events.Event;
import org.pureacc.app.domain.service.DomainEventPublisher;
import org.pureacc.app.domain.service.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class SpringEventPublisher implements EventPublisher {
	private final ApplicationEventPublisher applicationEventPublisher;

	SpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
		DomainEventPublisher.setPublisher(this);
	}

	@Override
	public void publish(Event event) {
		applicationEventPublisher.publishEvent(event);
	}
}
