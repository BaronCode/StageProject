package com.stage.stageProject.ActivitiesMgmt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ActivityServiceImpl implements ActivityService {
	
	@Autowired
	private ActivityRepo repo;

	@Override
	public Activity saveActivity(Activity act) {
		return repo.save(act);
	}

	@Override
	public List<Activity> fetchActivityList() {
		return (List<Activity>) repo.findAll();
	}

	@Override
	public Activity updateActivity(Activity act, int priority) {
		return null;
	}

	@Override
	public void deleteActivityById(int id) {
		repo.deleteById(id);
	}
	
	
}
