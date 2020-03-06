package infra.exceptionhandling;

public interface TestCommand {
	void domainException(String key, Object... params);

	void systemException();
}
