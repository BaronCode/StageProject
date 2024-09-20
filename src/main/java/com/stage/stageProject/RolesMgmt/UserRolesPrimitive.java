package com.stage.stageProject.RolesMgmt;

public class UserRolesPrimitive {
	private String username;
	private ROLES role;
	
	public UserRolesPrimitive() {}
	
	public UserRolesPrimitive(String usn, ROLES r) {
		username = usn;
		role = r;
	}

	public String getUsername() {
		return username;
	}
	public ROLES getRole() {
		return role;
	}
}
