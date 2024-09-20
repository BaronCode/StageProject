package com.stage.stageProject.RolesMgmt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class UserRolesServiceImpl implements UserRolesService {

	@Autowired
	private UserRolesRepo repo;
	
	@Override
	public UserRoles saveRoles(UserRoles roles) {
		return repo.save(roles);
	}

	@Override
	public List<UserRoles> fetchRolesList() {
		return (List<UserRoles>) repo.findAll();
	}

	@Override
	public UserRoles updateRoles(UserRoles roles, UserRolesPrimitive primitive) {
		return null;
	}

	@Override
	public void deleteRolesById(UserRolesPrimitive primitive) {
		repo.deleteById(primitive);
	}
	
	
	
}
