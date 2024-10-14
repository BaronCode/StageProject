package com.stage.stageProject.ActivitiesMgmt;

import lombok.Getter;

/**
 * Synthesis class for an Activity.
 * <br>Getter methods are generated via Lombok - call them using get + capitalized variable nome + ().
 * @deprecated
 */
@Getter
public class ActivityDetails {
	/**
	 * Activity name.
	 */
	private final String name;
	/**
	 * Activity ID.
	 */
	private final int id;
	/**
	 * Activity priority.
	 */
	private final PRIORITY priority;

	/**
	 * Constructor.
	 * @param activity the Activity whose fields are being synthesized.
	 * @deprecated
	 */
	public ActivityDetails(Activity activity) {
		name = activity.getName();
		id = activity.getId();
		priority = activity.getPriority();
	}

	/**
	 * Returns a String representation of this ActivityDetails.
	 * @return a String representation of this ActivityDetails.
	 * @deprecated
	 */
	public String toString() {
		return "\n" + name +
		"\n" + id +
		"\n" + priority;
	}


}
