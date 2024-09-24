package com.stage.stageProject.IntersectionMgmt;

import com.stage.stageProject.ActivitiesMgmt.Activity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntersectionDetails {
	private String name;
	private int id;
	
	public IntersectionDetails(Activity a) {
		name = a.getName();
		id = a.getId();
	}


}
