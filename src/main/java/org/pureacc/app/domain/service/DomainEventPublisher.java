package org.pureacc.app.domain.service;

import org.pureacc.app.domain.events.Event;

public class DomainEventPublisher {
	private static EventPublisher eventPublisher;

	public static void setPublisher(EventPublisher eventPublisher) {
		DomainEventPublisher.eventPublisher = eventPublisher;
	}

	public static void publish(Event event) {
		eventPublisher.publish(event);
	}
}
