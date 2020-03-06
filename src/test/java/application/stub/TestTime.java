package application.stub;

import java.time.Instant;

import org.pureacc.app.domain.service.Time;

public class TestTime implements Time {
	private static final Instant NOW = Instant.now();

	@Override
	public Instant now() {
		return NOW;
	}
}
