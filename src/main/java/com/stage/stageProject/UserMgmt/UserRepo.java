package com.stage.stageProject.UserMgmt;

import java.util.Optional;

import com.stage.stageProject.ActivitiesMgmt.Activity;
import com.stage.stageProject.ActivitiesMgmt.ActivityServiceImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository utility class.
 * <br>Indexes User entries in database table via JpaRepository using Username as index.
 * @see User
 * @see UserServiceImpl
 */
@Repository
public interface UserRepo extends JpaRepository<User, String> {
	/**
	 * Finds a User by its name.
	 * @param username the name of the User.
	 * @return a User with the matching name. Null if not found.
	 */
	Optional<User> findByName(String username); 
}
