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
	
	public Message(){}
	public Message(Activity activity, User author, String body, LocalDateTime t, int id) {
		this.id = id;
		this.author = author;
		this.body = body;
		timestamp = t;
		this.activity = activity;
	}
	
	public int getId() {
		return id;
	}
	public User getAuthor() {
		return author;
	}
	public String getBody() {
		return body;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	@Override
	public String toString() {
		return "";
	}
}
