package com.stage.stageProject.IntersectionMgmt;

import com.stage.stageProject.ActivitiesMgmt.Activity;

public class IntersectionDetails {
	private String name;
	private int id;
	
	public IntersectionDetails(Activity a) {
		name = a.getName();
		id = a.getId();
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

}
