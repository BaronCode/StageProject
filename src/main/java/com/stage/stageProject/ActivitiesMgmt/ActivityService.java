package com.stage.stageProject.ActivitiesMgmt;


import java.util.List;

/**
 * Service layer is where all the business logic lies
 */

public interface ActivityService {
	
	public Activity saveActivity(Activity act);
	public List<Activity> fetchActivityList();
	public Activity updateActivity(Activity act, int priority);
	public void deleteActivityById(int id);
}


