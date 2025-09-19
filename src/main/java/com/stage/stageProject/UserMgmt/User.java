package com.stage.stageProject.UserMgmt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * User entity class.
 * <br>Allows the creation of a User - either from database or from user-input data in registration page.
 * <br>Getters and setters methods are generated via Lombok - call them using get | set + capitalized variable nome + ().
 * <br>Each User has a name, an email and a password.
 *
 */

@Entity @NoArgsConstructor @Table(name = "users")
public class User {
	@NotNull @Id @Getter @Setter	private 	String 		name;
	@NotNull @Getter @Setter		private 	String 		email;
	@NotNull @Getter				private 	String 		password;
	@NotNull						private 	boolean 	active;

	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		this.active = true;
	}
}
