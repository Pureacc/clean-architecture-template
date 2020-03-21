package infra.exceptionhandling;

import org.pureacc.app.vocabulary.annotation.Query;
import org.pureacc.app.vocabulary.exception.DomainException;
import org.pureacc.app.vocabulary.exception.SystemException;

@Query
public class TestQueryImpl implements TestQuery {
	@Override
	public void domainException(String key, Object... params) {
		throw new DomainException(key, params);
	}

	@Override
	public void systemException() {
		throw new SystemException("An error occurred");
	}
}
