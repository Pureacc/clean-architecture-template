package org.pureacc.app.infra.audit;

import java.time.Instant;

final class AuditFailure {
	private final Object principal;
	private final Instant timestamp;
	private final Class targetClass;
	private final String methodName;
	private final Object argument;
	private final Exception exception;

	private AuditFailure(Builder builder) {
		principal = builder.principal;
		timestamp = builder.timestamp;
		targetClass = builder.targetClass;
		methodName = builder.methodName;
		argument = builder.argument;
		exception = builder.exception;
	}

	public Object getPrincipal() {
		return principal;
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public Class getTargetClass() {
		return targetClass;
	}

	public String getMethodName() {
		return methodName;
	}

	public Object getArgument() {
		return argument;
	}

	public Exception getException() {
		return exception;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static final class Builder {
		private Object principal;
		private Instant timestamp;
		private Class targetClass;
		private String methodName;
		private Object argument;
		private Exception exception;

		private Builder() {
		}

		public Builder withPrincipal(Object principal) {
			this.principal = principal;
			return this;
		}

		public Builder withTimestamp(Instant timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder withTargetClass(Class targetClass) {
			this.targetClass = targetClass;
			return this;
		}

		public Builder withMethodName(String methodName) {
			this.methodName = methodName;
			return this;
		}

		public Builder withArgument(Object argument) {
			this.argument = argument;
			return this;
		}

		public Builder withException(Exception exception) {
			this.exception = exception;
			return this;
		}

		public AuditFailure build() {
			return new AuditFailure(this);
		}
	}
}
