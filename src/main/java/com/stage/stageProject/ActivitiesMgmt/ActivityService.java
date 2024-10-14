package com.stage.stageProject.ActivitiesMgmt;


import java.util.List;

/**
 * Service layer for Activity entity class [ASL].
 * @see ActivityServiceImpl
 * @see ActivityRepo
 */
public interface ActivityService {
	/**
	 * Saves a new Activity to a JpaRepository repo.
	 * @param act the Activity to be saved.
	 * @return the saved Activity.
	 */
	Activity saveActivity(Activity act);

	/**
	 * Fetches all the Activity entries.
	 * @return a list containing all the Activity entries.
	 */
	List<Activity> fetchActivityList();

	/**
	 * Deletes an Activity referenced by its ID.
	 * @param id the id of the Activity.
	 */
	void deleteActivityById(int id);
}


