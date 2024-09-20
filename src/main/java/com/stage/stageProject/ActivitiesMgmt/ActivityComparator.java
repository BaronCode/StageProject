package com.stage.stageProject.ActivitiesMgmt;

import java.util.Comparator;

public class ActivityComparator implements Comparator<Activity> {

	@Override
	public int compare(Activity o1, Activity o2) {
		if (o1.getStatus()==o2.getStatus()) return 0;
		else if (o1.getStatus()==null) return -1;
		else if (o2.getStatus()==null) return -2;
		else return o1.getStatus().compareTo(o2.getStatus());
	}

}
