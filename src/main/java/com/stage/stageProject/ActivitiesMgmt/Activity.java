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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
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

}
