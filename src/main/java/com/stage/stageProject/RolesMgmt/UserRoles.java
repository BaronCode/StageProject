package com.stage.stageProject.RolesMgmt;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
@Entity
@IdClass(UserRolesPrimitive.class)
@Table(name = "user_roles")
public class UserRoles {
	@Id
	private String username;
	
	@Id
	@Enumerated(EnumType.STRING)
	private ROLES role;
		
	public UserRoles() {}
	
	public UserRoles(String n, ROLES id) {
		username = n;
		role = id;
	}

	public String toString() {
		return "User " + username + " has role " + role;
	}

	public String getName() {
		return username;
	}
	public ROLES getRole() {
		return role;
	}
}

