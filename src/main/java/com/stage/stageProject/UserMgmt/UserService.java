package com.stage.stageProject.UserMgmt;

import org.springframework.stereotype.Service;

@Service
public interface UserService  {
    User createUser(User user);
    User getUserByEmail(String email);
    String getNameByEmail(String email);
}


