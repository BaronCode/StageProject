package com.stage.stageProject.RolesMgmt;


import java.util.List;

/**
 * Service layer is where all the business logic lies
 */

public interface UserRolesService {
	
	public UserRoles saveRoles(UserRoles roles);
	public List<UserRoles> fetchRolesList();
	public UserRoles updateRoles(UserRoles roles, UserRolesPrimitive primitive);
	public void deleteRolesById(UserRolesPrimitive primitive);
}


