package com.stage.stageProject.ActivitiesMgmt;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.stage.stageProject.IntersectionMgmt.Intersection;
import com.stage.stageProject.UserMgmt.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "activities")
public class Activity {
	@Id
	private int id;
	private String name;
	private String description;
	@Enumerated(EnumType.STRING)
	private PRIORITY priority;
	@Enumerated(EnumType.STRING)
	private STATUS status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_name")
	private User user;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delegate")
	private User delegate;
	
    @OneToMany(mappedBy = "activity",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Intersection> intersections = new ArrayList<>();

	public Activity() {
		id = 0;
	}
	
	public Activity(int id, String n, PRIORITY p, User u) {
		this.id = id;
		name = n;
		description = "";
		priority = p;
		user = u;
		status = null;
		delegate = null;
	}
	
	public String consoleToString() {
		return "Activity " + name + " [ID=" + id + "] [PRIORITY=" + priority + "] [CREATOR=" + user.getName() + "]";
	}
	
	public String toString() {
		return getName();
	}

	public String notificationToString() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("Name", name);
		map.put("Id", String.valueOf(id));
		map.put("Priority", priority.toString());
		map.put("Creator", user.getName());
		return map.toString().replace(", ", "\n").replace("=", ": ").replace("{", "").replace("}", "");
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public PRIORITY getPriority() {
		return priority;
	}

	public STATUS getStatus() {
		return status;
	}

	public User getUser() {
		return user;
	}

	public User getDelegate() {
		return delegate;
	}

	public List<Intersection> getIntersections() {
		return intersections;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPriority(PRIORITY priority) {
		this.priority = priority;
	}

	public void setStatus(STATUS status) {
		this.status = status;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setDelegate(User delegate) {
		this.delegate = delegate;
	}

	public void setIntersections(List<Intersection> intersections) {
		this.intersections = intersections;
	}
}
