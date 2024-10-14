package com.stage.stageProject.UserMgmt;

import java.util.List;

import com.stage.stageProject.ActivitiesMgmt.ActivityRepo;
import com.stage.stageProject.ActivitiesMgmt.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stage.stageProject.ActivitiesMgmt.Activity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * Implementation of the User Service Layer [USL].
 * @see UserService
 * @see UserRepo
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * Linked User Repo.
	 */
	@Autowired
	private UserRepo repo;
	/**
	 * The User's persistence monitor.
	 */
	@Autowired
	private EntityManager entityManager;

	/**
	 * Saves a new User to a JpaRepository repo.
	 * @param user the User to be saved.
	 * @return the saved User.
	 */
	@Override
	public User saveUser(User user) {
		return repo.save(user);
	}

	/**
	 * Fetches all the User entries.
	 * @return a List containing all the User entries.
	 */
	@Override
	public List<User> fetchUserList() {
		return repo.findAll();
	}

	/**
	 * Deletes a User referenced by its username.
	 * @param name the name of the User.
	 */
	@Override
	public void deleteUserById(String name) {
		repo.deleteById(name);
	}

	/**
	 * Adds a User to the User's persistence monitor.
	 * @param user the name of the User.
	 */
	@Transactional
	@Override
	public void persist(String user) {
		entityManager.persist(repo.getReferenceById(user));
	}

	/**
	 * Merges the updates of the User's persistence monitor.
	 * @param user the name of the User.
	 */
	@Transactional
	@Override
	public void mergeUserAndActivity(User user) {
		entityManager.merge(user);
	}

	/**
	 * Removes from the database a User and all the Activity entries created by the User.
	 * @param name the name of the User.
	 */
	@Transactional
	@Override
	public void removeUserAndActivity(String name) {
		User u = repo.getReferenceById(name);
		entityManager.remove(entityManager.contains(u) ? u : entityManager.merge(u)); 
	}

	/**
	 * Refreshes a User persistence monitor.
	 * @param user the User to be refreshed.
	 */
	@Transactional
	@Override
	public void refreshUser(User user) {
		entityManager.refresh(user); 
	}

	/**
	 * Detaches a User from the persistence monitor.
	 * @param user the User that is going to be detached.
	 */
	@Transactional
	@Override
	public void detachUser(User user) {
		entityManager.detach(user); 
	}

	/**
	 * Removes a persistence association between a User and an Activity.
	 * <br>Should not be used.
	 * @param name the User's name.
	 * @param activity the activity that's going to be removed from the User's Activity list.
	 */
	@Transactional
	@Override
	public void removeActivityFromUser(String name, Activity activity) {
		User user = repo.getReferenceById(name);
		user.getActivities().remove(activity);
        if (!entityManager.contains(user)) {
            entityManager.merge(user); 
        }
	}
}
