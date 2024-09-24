package com.stage.stageProject.Notifications;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notifications")
public class Notification {
	@Id
	private int id;
	private String title;
	private String body;
	private LocalDateTime timestamp;
	@Setter
	private boolean status;

	@Override
	public String toString() {
		return "[" + timestamp.toString() +"] " + title + ": " + body;
	}
}
