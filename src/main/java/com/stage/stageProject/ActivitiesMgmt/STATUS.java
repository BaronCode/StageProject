package com.stage.stageProject.ActivitiesMgmt;

/**
 * Enum containing all the possible Activity statuses.
 * @see Activity
 */
public enum STATUS {
	/**
	 * Completed: completed.
	 */
	COMPLETED,
	/**
	 * In progress: started.
	 */
	IN_PROGRESS,
	/**
	 * Accepted: accepted assignation, not yet started.
	 */
	ACCEPTED,
	/**
	 * Refused: refused assignation.
	 */
	REFUSED,
	/**
	 * Pending: pending approval by the User.
	 */
	PENDING,
	/**
	 * Unassigned: Activity has not been assigned.
	 */
	UNASSIGNED
}
