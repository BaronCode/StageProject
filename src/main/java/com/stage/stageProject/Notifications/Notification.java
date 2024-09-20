package com.stage.stageProject.Notifications;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifications")
public class Notification {
	@Id
	private int id;
	private String title;
	private String body;
	private LocalDateTime timestamp;
	private boolean status;
	
	public Notification(){}
	public Notification(int id, LocalDateTime t, boolean s, String tl, String b) {
		this.id = id;
		title = tl;
		body = b;
		timestamp = t;
		status = s;
	}
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getBody() {
		return body;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "[" + timestamp.toString() +"] " + title + ": " + body;
	}
}
