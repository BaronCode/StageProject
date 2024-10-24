package com.stage.stageProject.UserMgmt;


import java.util.List;

import org.springframework.stereotype.Service;

import com.stage.stageProject.ActivitiesMgmt.Activity;

/**
 * Service layer for User entity class [USL].
 * @see UserServiceImpl
 * @see UserRepo
 */
@Service
public interface UserService  {
    /**
     * Saves a new User to a JpaRepository repo.
     * @param user the User to be saved.
     * @return the saved User.
     */
	User saveUser(User user);

    /**
     * Gets a User by its id
     * @param id the User id
     * @return a User with the matching ID
     */
    User getUser(String id);

    /**
     * Fetches all the User entries.
     * @return a List containing all the User entries.
     */
	List<User> fetchUserList();

    /**
     * Deletes a User referenced by its username.
     * @param name the name of the User.
     */
    void deleteUserById(String name);

    /**
     * Adds a User to the User's persistence monitor.
     * @param name the name of the User.
     */
    void persist(String name);

    /**
     * Merges the updates of the User's persistence monitor.
     * @param user the name of the User.
     */
    void mergeUserAndActivity(User user);

    /**
     * Removes from the database a User and all the Activity entries created by the User.
     * @param user the name of the User.
     */
    void removeUserAndActivity(String user);

    /**
     * Refreshes a User persistence monitor.
     * @param user the User to be refreshed.
     */
    void refreshUser(User user);

    /**
     * Detaches a User from the persistence monitor.
     * @param user the User that is going to be detached.
     */
    void detachUser(User user);

    /**
     * Removes a persistence association between a User and an Activity.
     * <br>Should not be used.
     * @param name the User's name.
     * @param activity the activity that's going to be removed from the User's Activity list.
     */
    void removeActivityFromUser(String name, Activity activity);
}


