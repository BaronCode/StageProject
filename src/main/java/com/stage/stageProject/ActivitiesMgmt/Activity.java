package com.stage.stageProject.ActivitiesMgmt;

import java.time.LocalDateTime;
import java.util.Date;

import com.stage.stageProject.UserMgmt.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Activity entity class.
 * <br>Allows the creation of an Activity - either from database or from user-input data.
 * <br>Getters and setters methods are generated via Lombok - call them using get | set + capitalized variable nome + ().
 * <br>Each Activity has an ID, a name, a description, an expiry date, a priority, a status, a creator and a delegate.
 *
 */

@Getter @Setter @Entity @NoArgsConstructor @Table(name = "activities")
public class Activity {

	@Id	@NotNull 										private 	int 			id;
	@NotNull 											private 	String 			name;
	@NotNull											private 	String 			description;
														private 	LocalDateTime 	expiring;
	@Enumerated(EnumType.STRING) @NotNull 				private 	PRIORITY 		priority;
	@Enumerated(EnumType.STRING) @NotNull				private 	STATUS 			status;
	@ManyToOne @JoinColumn(name = "creator") @NotNull	private 	User 			creator;
	@ManyToOne @JoinColumn(name = "delegate")			private 	User 			delegate;

	public Activity(int id, String name, PRIORITY priority, User creator) {
		this.id = id;
		this.name = name;
		this.priority = priority;
		this.creator = creator;

		description = "";
		status = STATUS.UNASSIGNED;
		delegate = null;
		expiring = null;
	}

	public Activity(int id, String name, String description, LocalDateTime expiring, PRIORITY priority, STATUS status, User creator, User delegate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.expiring = expiring;
		this.priority = priority;
		this.status = status;
		this.creator = creator;
		this.delegate = delegate;
	}

}
