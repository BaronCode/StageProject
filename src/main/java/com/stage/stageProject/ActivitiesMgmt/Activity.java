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
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * Activity entity class.
 * <br>Allows the creation of an Activity - either from database or from user-input data.
 * <br>Getters and setters methods are generated via Lombok - call them using get | set + capitalized variable nome + ().
 * <br>Each Activity has an ID, a name, a description, a priority, a status, a creator [user field], and a delegate [user who was first assigned the activity].
 *
 * @see PRIORITY
 * @see STATUS
 * @see ActivityRepo
 * @see User
 */
@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "activities")
public class Activity {
	/**
	 * Activity ID.
	 */
	@Id
	@NotNull
	private int id;
	/**
	 * Activity name.
	 */
	@NotNull
	private String name;
	/**
	 * Activity description.
	 */
	private String description;
	/**
	 * Activity priority.
	 */
	@Enumerated(EnumType.STRING)
	@NotNull
	private PRIORITY priority;
	/**
	 * Activity status.
	 */
	@Enumerated(EnumType.STRING)
	@NotNull
	private STATUS status;

	/**
	 * Activity creator. Uses referential integrity with User table.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator_name")
	@NotNull
	private User user;
	/**
	 * Activity delegate [the first User who was first assigned the Activity]. Uses referential integrity with User table.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delegate")
	private User delegate;

	/**
	 * One-to-many referential integrity with Intersection table. Managed by Hibernate.
	 * @see Intersection
	 */
    @OneToMany(mappedBy = "activity",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Intersection> intersections = new ArrayList<>();

	/**
	 * Default Activity constructor. No description, delegate or status provided.
	 * @param id Activity ID.
	 * @param n Activity name.
	 * @param p Activity priority [enumerated].
	 * @param u Activity creator.
	 */
	public Activity(int id, String n, PRIORITY p, User u) {
		this.id = id;
		name = n;
		description = "";
		priority = p;
		user = u;
		status = STATUS.UNASSIGNED;
		delegate = null;
	}

	/**
	 * Complete Activity constructor.
	 * @param id Activity ID.
	 * @param n Activity name.
	 * @param d Activity description.
	 * @param p Activity priority.
	 * @param s Activity status.
	 * @param u Activity creator.
	 * @param del Activity delegate [User who was first assigned the activity].
	 */
	public Activity(int id, String n, String d, PRIORITY p, STATUS s, User u, User del) {
		this.id = id;
		name = n;
		description = d;
		priority = p;
		user = u;
		status = s;
		delegate = del;
	}

	/**
	 * Only for debug purposes. Prints the Activity to the console.
	 * @return a new String representing this Activity.
	 */
	public String consoleToString() {
		return "Activity " + name + " [ID=" + id + "] [PRIORITY=" + priority + "] [CREATOR=" + user.getName() + "]";
	}

	/**
	 * Returns the name of the Activity.
	 * @return a String with the name of the Activity.
	 */
	public String toString() {
		return getName();
	}

	/**
	 * Notification-format representation of this Activity.
	 * @return a new String representing this Activity.
	 */
	public String notificationToString() {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("Name", name);
		map.put("Id", String.valueOf(id));
		map.put("Priority", priority.toString());
		map.put("Creator", user.getName());
		return map.toString().replace(", ", "\n").replace("=", ": ").replace("{", "").replace("}", "");
	}

}
