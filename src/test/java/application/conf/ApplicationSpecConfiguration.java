package application.conf;

import org.pureacc.app.domain.repository.UserRepository;
import org.pureacc.app.domain.service.EventPublisher;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import application.factory.Users;
import application.mock.TestEventPublisher;
import application.stub.TestTime;

@TestConfiguration
public class ApplicationSpecConfiguration {
	@Bean
	Users users(UserRepository userRepository) {
		return new Users(userRepository);
	}

	@Primary
	@Bean
	EventPublisher testEventPublisher() {
		return new TestEventPublisher();
	}

	@Primary
	@Bean
	TestTime testTime() {
		return new TestTime();
	}
}
