package infra.exceptionhandling;

public interface TestQuery {
	void domainException(String key, Object... params);

	void systemException();
}
