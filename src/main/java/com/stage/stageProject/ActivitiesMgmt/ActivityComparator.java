package com.stage.stageProject.ActivitiesMgmt;

import java.util.Comparator;

/**
 * Class used to compare two Activity objects by their STATUS field.
 */
public class ActivityComparator implements Comparator<Activity> {

	/**
	 * Comparison between the STATUS field of two Activity objects.
	 * @param o1 the first object to be compared.
	 * @param o2 the second object to be compared.
	 * @return an integer-based comparison between the two objects.
	 */
	@Override
	public int compare(Activity o1, Activity o2) {
		if (o1.getStatus()==o2.getStatus()) return 0;
		else if (o1.getStatus()==null) return -1;
		else if (o2.getStatus()==null) return -2;
		else return o1.getStatus().compareTo(o2.getStatus());
	}

}
