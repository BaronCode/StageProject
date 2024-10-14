package com.stage.stageProject.ActivitiesMgmt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of the Activity Service Layer [ASL].
 * @see ActivityService
 * @see ActivityRepo
 */
@Service
public class ActivityServiceImpl implements ActivityService {

	/**
	 * Linked Activity Repo.
	 */
	@Autowired
	private ActivityRepo repo;

	/**
	 * Saves a new Activity to the ActivityRepo.
	 * @param act the Activity to be saved.
	 * @return the saved Activity.
	 */
	@Override
	public Activity saveActivity(Activity act) {
		return repo.save(act);
	}

	/**
	 * Returns all the Activity entries in the ActivityRepo.
	 * @return a list containing all the Activity entries.
	 */
	@Override
	public List<Activity> fetchActivityList() {
		return repo.findAll();
	}

	/**
	 * Deletes an Activity referenced by its ID from the ActivityRepo.
	 * @param id the id of the Activity.
	 */
	@Override
	public void deleteActivityById(int id) {
		repo.deleteById(id);
	}
	
	
}
