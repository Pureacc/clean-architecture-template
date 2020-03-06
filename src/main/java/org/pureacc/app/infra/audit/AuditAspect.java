package org.pureacc.app.infra.audit;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Optional;

import org.apache.commons.lang3.ClassUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.pureacc.app.vocabulary.Password;
import org.pureacc.app.vocabulary.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Order(0)
@Component
class AuditAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuditAspect.class);

	private final Auditor auditor;

	AuditAspect(Auditor auditor) {
		this.auditor = auditor;
	}

	@Pointcut("within(@(@org.pureacc.app.vocabulary.annotation.Audit *) *)")
	void nestedAuditOnClass() {
	}

	@Pointcut("execution(* *(..))")
	void method() {
	}

	@Around("method() && nestedAuditOnClass()")
	public Object aspect(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			Object result = joinPoint.proceed();
			auditSuccess(joinPoint, result);
			return result;
		} catch (Exception e) {
			auditFailure(joinPoint, e);
			throw e;
		}
	}

	private void auditSuccess(JoinPoint joinPoint, Object result) {
		try {
			doAuditSuccess(joinPoint, result);
		} catch (Exception e) {
			LOGGER.error("Audit failed", e);
		}
	}

	private void auditFailure(ProceedingJoinPoint joinPoint, Exception e) {
		try {
			doAuditFailure(joinPoint, e);
		} catch (Exception e1) {
			LOGGER.error("Audit failed", e1);
		}
	}

	private void doAuditSuccess(JoinPoint joinPoint, Object result) throws IllegalAccessException {
		hideSensitiveAttributes(result);
		AuditSuccess auditSuccess = AuditSuccess.newBuilder()
				.withPrincipal(getPrincipal())
				.withTimestamp(Instant.now())
				.withTargetClass(joinPoint.getTarget()
						.getClass())
				.withMethodName(joinPoint.getSignature()
						.getName())
				.withArgument(getArgument(joinPoint))
				.withResult(result)
				.build();
		auditor.audit(auditSuccess);
	}

	private void doAuditFailure(JoinPoint joinPoint, Exception e) throws IllegalAccessException {
		AuditFailure auditFailure = AuditFailure.newBuilder()
				.withPrincipal(getPrincipal())
				.withTimestamp(Instant.now())
				.withTargetClass(joinPoint.getTarget()
						.getClass())
				.withMethodName(joinPoint.getSignature()
						.getName())
				.withArgument(getArgument(joinPoint))
				.withException(e)
				.build();
		auditor.audit(auditFailure);
	}

	private Object getPrincipal() {
		return Optional.ofNullable(SecurityContextHolder.getContext()
				.getAuthentication())
				.map(Authentication::getPrincipal)
				.orElse("anonymous");
	}

	private Object getArgument(JoinPoint joinPoint) throws IllegalAccessException {
		Object[] args = joinPoint.getArgs();
		if (args.length == 1) {
			Object argument = args[0];
			hideSensitiveAttributes(argument);
			return argument;
		} else if (args.length == 0) {
			return null;
		} else {
			throw new SystemException("Unexpected number of arguments in audited execution");
		}
	}

	private void hideSensitiveAttributes(Object obj) throws IllegalAccessException {
		if (obj == null) {
			return;
		}
		Class klazz = obj.getClass();
		if (!ClassUtils.isPrimitiveOrWrapper(klazz) && !(obj instanceof String) && !(obj instanceof Enum)) {
			for (Field field : klazz.getDeclaredFields()) {
				field.setAccessible(true);
				Object f = field.get(obj);
				if (f instanceof Password) {
					f = Password.of("****");
					field.set(obj, f);
				} else {
					hideSensitiveAttributes(f);
				}
			}
		}
	}
}
