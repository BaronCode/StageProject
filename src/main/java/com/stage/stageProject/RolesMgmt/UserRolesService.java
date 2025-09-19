package com.stage.stageProject.RolesMgmt;

import com.stage.stageProject.UserMgmt.User;
import com.stage.stageProject.UserMgmt.UserRepo;

import java.util.List;

public interface UserRolesService {
    boolean existsAdmin(String username);
}


