package com.stage.stageProject.ActivitiesMgmt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.stage.stageProject.UserMgmt.User;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

/**
 * Activity repository utility class.
 * <br>Indexes Activity entries in database table via JpaRepository using ActivityID as index.
 * @see Activity
 * @see ActivityServiceImpl
 */
@Repository
public interface ActivityRepo extends JpaRepository<Activity, Integer> {

	/**
	 * Finds maximum ActivityID in a table in order to assign a higher one on the creation of a new Activity.
	 * @return the maximum ActivityID in a table.
	 */
	@Query("SELECT MAX(id) FROM Activity")
    int findMaxId();

	/**
	 * Finds any Activity entries in a table with matching STATUS field.
	 * @param status the STATUS to be searched.
	 * @return a List of Activity with matching STATUS field.
	 */
	@Query("SELECT new com.stage.stageProject.ActivitiesMgmt.Activity(id, name, description, priority, status, user, delegate) FROM Activity WHERE status=?1")
    List<Activity> findByStatus(STATUS status);

	/**
	 * Modifies an Activity.
	 * @param id the ID of the Activity.
	 * @param name new Activity name.
	 * @param description new Activity description.
	 * @param priority new Activity priority.
	 * @param status new Activity status.
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Activity SET name=?2, description=?3, priority=?4, status=?5 WHERE id=?1")
    void updateActivity(int id, String name, String description, PRIORITY priority, STATUS status);

	/**
	 * Sets a delegate for an Activity.
	 * @param id the ID of the Activity.
	 * @param delegate the new delegate of the Activity.
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Activity SET delegate=?2 WHERE id=?1")
    void updateDelegate(int id, User delegate);

	/**
	 * Changes the STATUS field of an Activity.
	 * @param id the ID of the Activity.
	 * @param status the new STATUS to be assigned.
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Activity SET status=?2 WHERE id=?1")
    void updateStatus(int id, STATUS status);

	/**
	 * Changes the PRIORITY field of an Activity.
	 * @param id the ID of the Activity.
	 * @param priority the new PRIORITY to be assigned.
	 */
	@Modifying
	@Transactional
	@Query("UPDATE Activity SET priority=?2 WHERE id=?1")
	void updatePriority(int id, PRIORITY priority);

}
