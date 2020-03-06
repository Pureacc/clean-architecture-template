package org.pureacc.app.infra.audit;

import static java.lang.String.format;

import java.util.Optional;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class Slf4jAuditor implements Auditor {
	private static final Logger LOGGER = LoggerFactory.getLogger(Slf4jAuditor.class);
	private static final String SUCCESS_TEMPLATE = "[SUCCESS] User '%s' performed '%s::%s' with argument '%s' and response '%s'";
	private static final String FAILURE_TEMPLATE = "[FAILURE] User '%s' performed '%s::%s' with argument '%s' and error '%s'";

	@Override
	public void audit(AuditSuccess success) {
		LOGGER.info(format(SUCCESS_TEMPLATE, success.getPrincipal(), success.getTargetClass()
						.getSimpleName(), success.getMethodName(), stringify(success.getArgument()),
				stringify(success.getResult())));
	}

	@Override
	public void audit(AuditFailure failure) {
		LOGGER.info(format(FAILURE_TEMPLATE, failure.getPrincipal(), failure.getTargetClass()
				.getSimpleName(), failure.getMethodName(), stringify(failure.getArgument()), failure.getException()
				.getMessage()));
	}

	private String stringify(Object object) {
		return Optional.ofNullable(object)
				.map(o -> new ReflectionToStringBuilder(object, new CustomStringStyle()).toString())
				.orElse("");
	}

	private static class CustomStringStyle extends RecursiveToStringStyle {
		public CustomStringStyle() {
			setUseShortClassName(true);
			setUseIdentityHashCode(false);
		}
	}
}
