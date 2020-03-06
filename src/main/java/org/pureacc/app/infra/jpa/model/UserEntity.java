package org.pureacc.app.infra.jpa.model;

import javax.persistence.*;

import org.pureacc.app.vocabulary.Password;
import org.pureacc.app.vocabulary.Username;

@Entity
@Table(name = "user")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String username;
	private String password;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Username getUsername() {
		return Username.of(username);
	}

	public void setUsername(Username username) {
		this.username = username.getValue();
	}

	public Password getPassword() {
		return Password.of(password);
	}

	public void setPassword(Password password) {
		this.password = password.getValue();
	}
}
