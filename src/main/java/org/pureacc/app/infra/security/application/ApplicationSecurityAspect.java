package org.pureacc.app.infra.security.application;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.pureacc.app.vocabulary.annotation.Allow;
import org.pureacc.app.vocabulary.annotation.SecuredResource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(3)
@Component
class ApplicationSecurityAspect {
	private final RoleSecurityControl roleSecurityControl;
	private final ResourceSecurityControl resourceSecurityControl;

	ApplicationSecurityAspect(RoleSecurityControl roleSecurityControl,
			ResourceSecurityControl resourceSecurityControl) {
		this.roleSecurityControl = roleSecurityControl;
		this.resourceSecurityControl = resourceSecurityControl;
	}

	@Pointcut("within(@(@org.pureacc.app.vocabulary.annotation.Secured *) *)")
	void nestedSecuredOnClass() {
	}

	@Pointcut("execution(* *(..))")
	void method() {
	}

	@Before("method() && nestedSecuredOnClass()")
	public void aspect(JoinPoint joinPoint) throws IllegalAccessException {
		checkAllowedRoles((MethodSignature) joinPoint.getSignature());
		checkSecuredResources(joinPoint.getArgs());
	}

	private void checkAllowedRoles(MethodSignature methodSignature) {
		Allow allow = AnnotationUtils.findAnnotation(methodSignature.getMethod(), Allow.class);
		roleSecurityControl.check(allow);
	}

	private void checkSecuredResources(Object[] args) throws IllegalAccessException {
		for (Object arg : args) {
			List<Field> securedFields = FieldUtils.getFieldsListWithAnnotation(arg.getClass(), SecuredResource.class);
			for (Field field : securedFields) {
				Object resourceValue = FieldUtils.readField(field, arg, true);
				resourceSecurityControl.check(resourceValue);
			}
		}
	}
}
