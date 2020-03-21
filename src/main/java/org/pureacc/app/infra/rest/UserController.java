package org.pureacc.app.infra.rest;

import static org.springframework.http.HttpStatus.CREATED;

import org.pureacc.app.application.api.CreateUser;
import org.pureacc.app.application.api.GetUser;
import org.pureacc.app.vocabulary.Password;
import org.pureacc.app.vocabulary.UserId;
import org.pureacc.app.vocabulary.Username;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@RestController
class UserController {
	private final CreateUser createUser;
	private final GetUser getUser;

	UserController(CreateUser createUser, GetUser getUser) {
		this.createUser = createUser;
		this.getUser = getUser;
	}

	@ResponseStatus(CREATED)
	@PostMapping("/api/user/register")
	RegisterWebResponse register(@RequestBody RegisterWebRequest webRequest) {
		CreateUser.Request request = CreateUser.Request.newBuilder()
				.withUsername(webRequest.getUsername())
				.withPassword(webRequest.getPassword())
				.build();
		CreateUser.Response response = createUser.execute(request);
		return new RegisterWebResponse(response.getUserId());
	}

	@GetMapping("/api/user")
	GetUserWebResponse get(@RequestParam("userId") long userId) {
		GetUser.Request request = GetUser.Request.newBuilder()
				.withUserId(UserId.of(userId))
				.build();
		GetUser.Response response = getUser.execute(request);
		return new GetUserWebResponse(request.getUserId(), response.getUsername());
	}

	static final class RegisterWebRequest {
		private final String username;
		private final String password;

		@JsonCreator
		RegisterWebRequest(@JsonProperty("username") String username, @JsonProperty("password") String password) {
			this.username = username;
			this.password = password;
		}

		public Username getUsername() {
			return Username.of(username);
		}

		public Password getPassword() {
			return Password.of(password);
		}
	}

	static final class RegisterWebResponse {
		private final long userId;

		RegisterWebResponse(UserId userId) {
			this.userId = userId.getValue();
		}

		public long getUserId() {
			return userId;
		}
	}

	static final class GetUserWebResponse {
		private final long userId;
		private final String username;

		GetUserWebResponse(UserId userId, Username username) {
			this.userId = userId.getValue();
			this.username = username.getValue();
		}

		public long getUserId() {
			return userId;
		}

		public String getUsername() {
			return username;
		}
	}
}
