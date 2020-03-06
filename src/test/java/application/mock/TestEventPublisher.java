package application.mock;

import java.util.LinkedList;
import java.util.Queue;

import org.pureacc.app.domain.events.Event;
import org.pureacc.app.domain.service.EventPublisher;

public class TestEventPublisher implements EventPublisher {
	private final Queue<Event> events = new LinkedList<>();

	@Override
	public void publish(Event event) {
		events.add(event);
	}

	public Event poll() {
		return events.poll();
	}

	public void clear() {
		events.clear();
	}
}
