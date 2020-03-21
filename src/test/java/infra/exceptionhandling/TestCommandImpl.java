package infra.exceptionhandling;

import org.pureacc.app.vocabulary.annotation.Command;
import org.pureacc.app.vocabulary.exception.DomainException;
import org.pureacc.app.vocabulary.exception.SystemException;

@Command
public class TestCommandImpl implements TestCommand {
	@Override
	public void domainException(String key, Object... params) {
		throw new DomainException(key, params);
	}

	@Override
	public void systemException() {
		throw new SystemException("An error occurred");
	}
}
