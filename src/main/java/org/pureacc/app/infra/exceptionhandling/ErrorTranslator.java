package org.pureacc.app.infra.exceptionhandling;

import java.util.Locale;

import org.pureacc.app.vocabulary.exception.DomainException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;

class ErrorTranslator {
	private final ResourceBundleMessageSource errorMessages;

	ErrorTranslator(ResourceBundleMessageSource errorMessages) {
		this.errorMessages = errorMessages;
	}

	String translate(DomainException exception) {
		return translate(exception.getKey(), exception.getParams());
	}

	String translate(String key, Object... params) {
		try {
			return errorMessages.getMessage(key, params, Locale.ENGLISH);
		} catch (NoSuchMessageException e) {
			return key;
		}
	}
}
