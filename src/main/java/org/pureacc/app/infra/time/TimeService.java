package org.pureacc.app.infra.time;

import java.time.Instant;

import org.pureacc.app.domain.service.DomainTime;
import org.pureacc.app.domain.service.Time;
import org.springframework.stereotype.Component;

@Component
class TimeService implements Time {
	TimeService() {
		DomainTime.setTime(this);
	}

	@Override
	public Instant now() {
		return Instant.now();
	}
}
