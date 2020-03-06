package org.pureacc.app.infra.exceptionhandling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
class ExceptionHandlingConfiguration {
	@Bean
	UseCaseExceptionAspect useCaseExceptionAspect(ErrorTranslator errorTranslator) {
		return new UseCaseExceptionAspect(errorTranslator);
	}

	@Bean
	ErrorTranslator domainExceptionTranslator(ResourceBundleMessageSource errorMessages) {
		return new ErrorTranslator(errorMessages);
	}

	@Bean
	ResourceBundleMessageSource errorMessages() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("org/pureacc/app/infra/exceptionhandling/errors");
		return source;
	}
}
