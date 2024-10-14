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

/**
 * User entity class.
 * <br>Allows the creation of a User - either from database or from user-input data in registration page.
 * <br>Getters and setters methods are generated via Lombok - call them using get | set + capitalized variable nome + ().
 * <br>Each User has a name, an email, a password, tbe Activity entries he created and the activities he is doing.
 *
 * @see Activity
 * @see Intersection
 */
@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
	/**
	 * Username.
	 */
	@Setter
    @Getter
    @Id
	private String name;
	/**
	 * User email.
	 */
	@Setter
    @Getter
    private String mail;
	/**
	 * User password.
	 */
	@Setter
	@Getter
	private String psw;

	/**
	 * One-to-many referential integrity with Activity table. Managed by Hibernate.
	 * @see Activity
	 */
	@Setter
    @Getter
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();

	/**
	 * One-to-many referential integrity with Intersection table. Managed by Hibernate.
	 * @see Intersection
	 */
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Intersection> intersections = new ArrayList<>();

	/**
	 * Complete User constructor.
	 * @param n User name
	 * @param e User email
	 * @param psw User password
	 */
	public User(String n, String e, String psw) {
		name = n;
		mail = e;
		this.psw = psw;
	}

	/**
	 * Only for debug purposes. Prints the User to the console.
	 * @return a new String representing this User.
	 */
	public String consoleToString() {
		return "User " + name + " [" + mail + "] PSW=" + psw;
	}

	/**
	 * Notification-format representation of this User.
	 * @return a new String representing this User.
	 */
	public String notificationToString() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("Name", name);
		map.put("Mail", mail);
		return map.toString().replace(", ", "\n").replace("=", ": ").replace("{", "").replace("}", "");
	}

	/**
	 * Returns the User's name.
	 * @return a String with the User's name.
	 */
	public String toString() {
		return getName();
	}

	/**
	 * Adds an Activity to the activity creation list.
	 * @param a the Activity to be added.
	 */
	public void addActivity(Activity a) {
		activities.add(a);
	}

}
