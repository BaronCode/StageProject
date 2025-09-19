package com.stage.stageProject.RolesMgmt;

import com.stage.stageProject.UserMgmt.UserServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UserRolesServiceImpl implements UserRolesService {
    private     final       UserRolesRepo   userRolesRepo;
    private     final       UserServiceImpl userService;

    public UserRolesServiceImpl(UserRolesRepo urr, UserServiceImpl us) {
        userRolesRepo = urr;
        userService = us;
    }

    @Override
    public boolean existsAdmin(String email) {
        return userRolesRepo.findById(new UserRolesPrimitive(userService.getUserByEmail(email), ROLES.ADMIN)).isPresent();
    }
}
