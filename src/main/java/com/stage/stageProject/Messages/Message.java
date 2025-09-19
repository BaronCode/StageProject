package com.stage.stageProject.Messages;

import java.util.Date;

import com.stage.stageProject.ActivitiesMgmt.Activity;
import com.stage.stageProject.RolesMgmt.ROLES;
import com.stage.stageProject.UserMgmt.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @Entity @NoArgsConstructor @AllArgsConstructor @Table(name = "messages")
public class Message {
	@NotNull @Id 												private 	int			id;
	@NotNull  													private 	String		body;
	@NotNull  													private 	Date 		timestamp;
	@NotNull @ManyToOne	@JoinColumn(name = "author") 			private 	User		author;
	@NotNull @ManyToOne	@JoinColumn(name = "activity") 			private 	Activity	activity;
}
