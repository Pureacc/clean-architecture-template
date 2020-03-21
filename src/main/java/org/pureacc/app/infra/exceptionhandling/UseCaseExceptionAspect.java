package org.pureacc.app.infra.exceptionhandling;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.pureacc.app.vocabulary.exception.DomainException;
import org.pureacc.app.vocabulary.exception.SystemException;
import org.pureacc.app.vocabulary.exception.UserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

@Aspect
@Order(1)
class UseCaseExceptionAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(UseCaseExceptionAspect.class);

	private final ErrorTranslator errorTranslator;

	UseCaseExceptionAspect(ErrorTranslator errorTranslator) {
		this.errorTranslator = errorTranslator;
	}

	@Pointcut("@within(org.pureacc.app.vocabulary.annotation.Command)")
	public void command() {
	}

	@Pointcut("@within(org.pureacc.app.vocabulary.annotation.Query)")
	public void query() {
	}

	@Pointcut("execution(* *(..))")
	void method() {
	}

	@Around("(command() || query()) && method()")
	public Object useCase(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			return joinPoint.proceed();
		} catch (DomainException domainException) {
			String message = errorTranslator.translate(domainException);
			throw new UserException(message);
		} catch (ConstraintViolationException constraintViolationException) {
			throw new UserException(translateConstraintViolation(constraintViolationException));
		} catch (SystemException systemException) {
			String message = errorTranslator.translate("error.generic");
			LOGGER.error(message, systemException);
			throw new UserException(message);
		}
	}

	private String translateConstraintViolation(ConstraintViolationException exception) {
		return exception.getConstraintViolations()
				.stream()
				.map(cv -> getField(cv.getPropertyPath()) + " " + cv.getMessage())
				.collect(Collectors.joining(", "));
	}

	private String getField(Path path) {
		return StreamSupport.stream(path.spliterator(), false)
				.skip(2)
				.map(Path.Node::getName)
				.collect(Collectors.joining(" "));
	}
}
