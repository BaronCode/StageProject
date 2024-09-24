package com.stage.stageProject.IntersectionMgmt;

import com.stage.stageProject.ActivitiesMgmt.Activity;
import com.stage.stageProject.UserMgmt.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "inter")
public class Intersection {
	@Id
	private int row;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "name")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Activity activity;

	public String toString() {
		return activity + " assigned to " + user;
	}
	public int getActivityId() {
		return activity.getId();
	}
}
