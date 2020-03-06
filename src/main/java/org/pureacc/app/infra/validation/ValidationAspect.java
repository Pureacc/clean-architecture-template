package org.pureacc.app.infra.validation;

import java.util.Set;

import javax.validation.*;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(2)
@Component
class ValidationAspect {
	private final Validator validator;

	ValidationAspect() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
		factory.close();
	}

	@Pointcut("@within(org.pureacc.app.vocabulary.annotation.Command)")
	public void command() {
	}

	@Pointcut("@within(org.pureacc.app.vocabulary.annotation.Query)")
	public void query() {
	}

	@Pointcut("execution(* *(..))")
	public void method() {
	}

	@Before("(command() || query()) && method()")
	public void useCase(JoinPoint joinPoint) {
		MethodSignature theSignature = (MethodSignature) joinPoint.getSignature();
		Set<ConstraintViolation<Object>> theViolations = validator.forExecutables()
				.validateParameters(joinPoint.getTarget(), theSignature.getMethod(), joinPoint.getArgs());
		if (theViolations.size() > 0) {
			throw new ConstraintViolationException(theViolations);
		}
	}
}
