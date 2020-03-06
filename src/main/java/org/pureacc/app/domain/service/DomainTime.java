package org.pureacc.app.domain.service;

import java.time.Instant;

public class DomainTime {
	private static Time time;

	public static void setTime(Time time) {
		DomainTime.time = time;
	}

	public static Instant now() {
		return time.now();
	}
}
