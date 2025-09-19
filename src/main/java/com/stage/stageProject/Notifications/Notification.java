package com.stage.stageProject.Notifications;

import java.time.LocalDateTime;

import com.stage.stageProject.ActivitiesMgmt.PRIORITY;
import com.stage.stageProject.UserMgmt.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @AllArgsConstructor @Table(name = "notifications")
public class Notification {
	@NotNull @Id 										private 	int 			id;
	@NotNull											private		String			body;
	@NotNull											private 	LocalDateTime 	timestamp;
	@NotNull @ManyToOne @JoinColumn(name = "sender")	private 	User 			sender;
	@NotNull @ManyToOne @JoinColumn(name = "receiver")	private 	User 			receiver;
	@NotNull 											private 	boolean 		status;

}
