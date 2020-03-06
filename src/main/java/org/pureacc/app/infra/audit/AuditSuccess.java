package org.pureacc.app.infra.audit;

import java.time.Instant;

final class AuditSuccess {
	private final Object principal;
	private final Instant timestamp;
	private final Class targetClass;
	private final String methodName;
	private final Object argument;
	private final Object result;

	private AuditSuccess(Builder builder) {
		principal = builder.principal;
		timestamp = builder.timestamp;
		targetClass = builder.targetClass;
		methodName = builder.methodName;
		argument = builder.argument;
		result = builder.result;
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

	public Object getResult() {
		return result;
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
		private Object result;

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

		public Builder withResult(Object result) {
			this.result = result;
			return this;
		}

		public AuditSuccess build() {
			return new AuditSuccess(this);
		}
	}
}
