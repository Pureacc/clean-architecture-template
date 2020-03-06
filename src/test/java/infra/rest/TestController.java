package infra.rest;

import org.pureacc.app.vocabulary.exception.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/api/test")
	public ResponseEntity<String> test() {
		throw new UserException("An error occurred");
	}
}
