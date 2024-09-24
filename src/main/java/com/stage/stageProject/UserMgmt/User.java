package com.stage.stageProject.UserMgmt;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.stage.stageProject.ActivitiesMgmt.Activity;
import com.stage.stageProject.IntersectionMgmt.Intersection;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
	@Setter
    @Getter
    @Id
	private String name;
	@Setter
    @Getter
    private String mail;
	@Setter
	@Getter
	private String psw;

    @Getter
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Intersection> intersections = new ArrayList<>();

	public User(String n, String e, String psw) {
		name = n;
		mail = e;
		this.psw = psw;
	}

	public String consoleToString() {
		return "User " + name + " [" + mail + "] PSW=" + psw;
	}
	
	public String notificationToString() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("Name", name);
		map.put("Mail", mail);
		return map.toString().replace(", ", "\n").replace("=", ": ").replace("{", "").replace("}", "");
	}
	
	public String toString() {
		return getName();
	}

    public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	public void addActivity(Activity a) {
		activities.add(a);
	}

}
