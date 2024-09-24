package com.stage.stageProject.RolesMgmt;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@IdClass(UserRolesPrimitive.class)
@Table(name = "user_roles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRoles {
	@Id
	private String username;
	
	@Id
	@Enumerated(EnumType.STRING)
	private ROLES role;

	public String toString() {
		return "User " + username + " has role " + role;
	}
}

