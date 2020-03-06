package org.pureacc.app.vocabulary.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Allow {
	enum Role {
		UNAUTHENTICATED,
		AUTHENTICATED,
		SYSTEM
	}

	Allow.Role[] value();
}
