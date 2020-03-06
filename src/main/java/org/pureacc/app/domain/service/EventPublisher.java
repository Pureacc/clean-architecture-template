package org.pureacc.app.domain.service;

import org.pureacc.app.domain.events.Event;

public interface EventPublisher {
	void publish(Event event);
}
