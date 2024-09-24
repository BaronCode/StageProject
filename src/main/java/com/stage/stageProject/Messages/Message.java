package com.stage.stageProject.Messages;

import java.time.LocalDateTime;

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
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "messages")
public class Message {
	@Id
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author")
	private User author;
	private String body;
	private LocalDateTime timestamp;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "activity")
	private Activity activity;

    @Override
	public String toString() {
		return "";
	}
}
