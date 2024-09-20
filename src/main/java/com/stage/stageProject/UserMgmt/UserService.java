package com.stage.stageProject.UserMgmt;


import java.util.List;

import org.springframework.stereotype.Service;

import com.stage.stageProject.ActivitiesMgmt.Activity;

/**
 * Service layer is where all the business logic lies
 */

@Service
public interface UserService  {
	
	public User saveUser(User user);
	public List<User> fetchUserList();
	public User updateUser(User user, String name);
	public void deleteUserById(String name);
	public void persist(String name);
    public void mergeUserAndActivity(User user);
    public void removeUserAndActivity(String user);
    public void refreshUser(User user);
    public void detachUser(User user);
    public void removeActivityFromUser(String name, Activity activity);
}


