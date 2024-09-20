package com.stage.stageProject.UserMgmt;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.stage.stageProject.ActivitiesMgmt.Activity;
import com.stage.stageProject.IntersectionMgmt.Intersection;
import com.stage.stageProject.RolesMgmt.ROLES;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {
	@Id
	private String name;
	private String mail;
	private String psw;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Intersection> intersections = new ArrayList<>();

    public User() {}
	
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

	public String getName() {
		return name;
	}
	
	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	public void addActivity(Activity a) {
		activities.add(a);
	}

	public String getMail() {
		return mail;
	}
	
	public String getPassword() {
		return psw;
	}
	
	public void setPassword(String newPsw) {
		psw = newPsw;
	}
	public void setMail(String newMail) {
		mail = newMail;
	}
	public void setName(String newName) {
		name = newName;
	}

	
}
